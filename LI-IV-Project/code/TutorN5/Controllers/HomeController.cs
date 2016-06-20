using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using TutorN5.Models.reis.aprendizagem.aulas;
using TutorN5.Models.reis.aprendizagem.exercicios;
using TutorN5.Models.reis;
using TutorN5.Models.utilizador;
using TutorN5.Models.tutor;
using System.Text;
using System.Collections;
using System.Threading.Tasks;
using Microsoft.Speech;
using System.Net;
using System.Text.RegularExpressions;

namespace TutorN5.Controllers
{
    public class HomeController : AsyncController
    {
        private AulaDAO aulaDAO = new AulaDAO();
        private PerguntaDAO pergDAO = new PerguntaDAO();
        private ExercicioDAO excDAO = new ExercicioDAO();
        private FonteDAO fonteDAO = new FonteDAO();
        private UtilizadorAulaDAO uaDAO = new UtilizadorAulaDAO();
        private UtilizadorReiDAO urDAO = new UtilizadorReiDAO();
        private ReiDAO reiDAO = new ReiDAO();
        private UtilizadorDAO userDAO = new UtilizadorDAO();
        private UtilizadorExercicioDAO ueDAO = new UtilizadorExercicioDAO();

        private static PreencherEspacos pe;
        private static Utilizador user;
        private static int REI_ATUAL;


        /*------------------------------------------------------------------------------------------------
         Home Page
         ------------------------------------------------------------------------------------------------*/
        public ActionResult Index()
        {
            Const.isAutenticated = false;

            Session.Clear();
            Session.RemoveAll();
            Session.Abandon();

            return View();
        }

        /*-----------------------------------------------------------------------------------------------*/


        /*--------------------------------------------------------------------------------------------
         Interatividade AVATAR - Muralhas
         --------------------------------------------------------------------------------------------*/

        [AsyncTimeout(1000)]
        public async Task<ActionResult> Falar()
        {
            Speaker speech = Speaker.getInstance();
            speech.Velocidade = -1;
            speech.Volume = 100;

            // Executar a síntese de voz
            speech.speakText(Personalidade.FALA_ATUAL);

            await Task.Delay(100);

            
            return View(string.Empty);
        }

        /*-----------------------------------------------------------------------------------------------*/


        /*------------------------------------------------------------------------------------------------
         Definições do utilizador
         ------------------------------------------------------------------------------------------------*/

        private static Boolean acabadoDeRegistar = false;
        public ActionResult Definicoes()
        {
            List<SelectListItem> items = new List<SelectListItem>();

            if (user.ModoInt == 0)
            {
                items.Add(new SelectListItem { Text = "Baixo", Value = "0", Selected = true });
                items.Add(new SelectListItem { Text = "Alto", Value = "1"});
            }
            else
            {
                items.Add(new SelectListItem { Text = "Baixo", Value = "0" });
                items.Add(new SelectListItem { Text = "Alto", Value = "1", Selected = true });
            }


            ViewBag.Interatividades = items;

            if (acabadoDeRegistar == true)
            {
                Personalidade.FALA_ATUAL += Personalidade.DEFINICOES;
                acabadoDeRegistar = false;
            }
            else
            {
                Personalidade.FALA_ATUAL = Personalidade.DEFINICOES;
            }

            return View(user);
        }

        //
        // POST: /Home/GuardarDefinicoes
        [HttpPost]
        public ActionResult GuardarDefinicoes(Utilizador model, string Interatividades)
        {
            try
            {
                var urentry = userDAO.Utilizador.Where(us => us.Id == user.Id).ToList();
                urentry.ForEach(us => us.ModoInt = Int32.Parse(Interatividades));
                urentry.ForEach(us => us.Nome= model.Nome);
                urentry.ForEach(us => us.Idade = model.Idade);

                userDAO.SaveChanges();

                user = userDAO.Utilizador.Find(user.Id); // Buscar dados atualizados do utilizador para variável em cache

            } catch (System.Data.SqlClient.SqlException) { }

            return RedirectToAction("Perfil");
        }

        /*------------------------------------------------------------------------------------------------
         Autenticação e Registo
         ------------------------------------------------------------------------------------------------*/

        //
        // GET: /Account/Login
        [AllowAnonymous]
        public ActionResult Login(string returnUrl)
        {
            ViewBag.ReturnUrl = returnUrl;
            return View("Login");
        }

        //
        // POST: /Account/Login
        [HttpPost]
        [AllowAnonymous]
        [ValidateAntiForgeryToken]
        public ActionResult Login(Utilizador model, string returnUrl)
        {
            if (ModelState.IsValid)
            {
                user = null;
                var users = userDAO.Utilizador;

                // Verificar existência de utilizador
                foreach(Utilizador u in users){
                    if (u.Nome.Equals(model.Nome))
                    {
                        // Atualizar o ficheiro const para validar autenticação para o exterior (principalmente para acesso das scripts .css)
                        Const.isAutenticated = true;
                        Const.UserName = u.Nome;
                        user = u;
                        break;
                    }
                }        

                if (user != null)
                {
                    return RedirectToAction("Perfil");
                }
                else
                {
                    ModelState.AddModelError("", "Nome inválido.");
                }
            }

            // If we got this far, something failed, redisplay form
            return View("Login");
        }

        //
        // GET: /Account/Register
        [AllowAnonymous]
        public ActionResult Register()
        {
            return View("Register");
        }

        //
        // POST: /Account/Register
        [HttpPost]
        [AllowAnonymous]
        [ValidateAntiForgeryToken]
        public ActionResult Register(Utilizador model)
        {
            if (ModelState.IsValid)
            {
                var useraux = new Utilizador() { Nome = model.Nome, Idade = model.Idade };
                bool exists = false;
                var users = userDAO.Utilizador;

                // Verificar existência de utilizador
                foreach (Utilizador u in users)
                {
                    if (u.Nome.Equals(useraux.Nome))
                    {
                        exists = true;
                        break;
                    }
                }

                if (exists != true)
                {
                    // Inicializar parametros relativos ao utilizador
                    useraux.ProgressoGeral = 0;
                    useraux.ModoInt = 0; // super interatividade desativada por defeito
                    useraux.TotalExercicios = 0;
                    useraux.ExerciciosPositivos = 0;
                    useraux.TotalAlineas = 0;
                    useraux.TotalAlineasCorretas = 0;
                    useraux.TotalAulas = 0;

                    userDAO.Utilizador.Add(useraux);
                    
                    // Atualizar o utilizador atual em cache no controller
                    user = useraux;

                    int objetosEscritos = userDAO.SaveChanges();

                    if (objetosEscritos == 1)
                    {
                        acabadoDeRegistar = true;
                        Personalidade.FALA_ATUAL = Personalidade.APRESENTACAO;

                        Const.isAutenticated = true; // Passa automaticamente a estar autenticado
                        Const.Idade = (int)model.Idade;
                        Const.UserName = model.Nome;

                        Personalidade.PRIMEIRA_AULA = true;
                        Personalidade.PRIMEIRO_EXERCICIO = true;

                        return RedirectToAction("Definicoes", "Home");
                    }
                }
            }

            // Se chegamos aqui algo falhou, mostrar novamente formuário de registo
            return View("Register");
        }

        /*------------------------------------------------------------------------------------------------*/


        /*------------------------------------------------------------------------------------------------
         Estatísticas e Histórico do Utilizador
        -------------------------------------------------------------------------------------------------*/

        public ActionResult Estatisticas()
        {
            //Criar o view model e por lá os dados das tabelas marcadas com HistEst na base de dados
            Personalidade.FALA_ATUAL = Personalidade.ESTATISTICAS;
            EstatisticaViewModel evm = new EstatisticaViewModel(user);
            return View(evm);
        }

        public ActionResult Historico()
        {
            Personalidade.FALA_ATUAL = Personalidade.HISTORICO;
            HistoricoViewModel hvm = new HistoricoViewModel(excDAO, aulaDAO, uaDAO, ueDAO, reiDAO,(int)user.Id);
            return View(hvm);
        }

        /*-------------------------------------------------------------------------------------------------*/


        /*-----------------------------------------------------------------------------------------------
          Perfil do Utilizador
        ------------------------------------------------------------------------------------------------*/
        public ActionResult Perfil()
        {
            Personalidade.FALA_ATUAL = Personalidade.PERFIL;

            user = userDAO.Utilizador.Find(user.Id);

            // Atualizar valores das métricas para o seu estado mais recente
            Metricas.TE = excDAO.Exercicio.Count();
            Metricas.TA = aulaDAO.Aula.Count();
            Metricas.TR = reiDAO.Rei.Count();

            Metricas.TER = (int)user.TotalExercicios; // ---> Apenas após resolver pela primeira vez exercício

            Metricas.TAA = (int)user.TotalAulas;      // ---> Apenas após assistir pela primeira a aula
        
            // Atualizar concreta e explicitamente o progresso geral do utilizador com sessão iniciada
            Const.ProgressoDoUtilizador = Metricas.getPGeral();

            PerfilUtilizadorViewModel pf = new PerfilUtilizadorViewModel(reiDAO);
            return View(pf);
        }

        /*------------------------------------------------------------------------------------------------*/


        /*-----------------------------------------------------------------------------------------------
          Perfil do Rei
        ------------------------------------------------------------------------------------------------*/
        public ActionResult PerfilDoRei(int idRei)
        {
            REI_ATUAL = idRei;

            using (var context = new UtilizadorReiDAO())
            {

                // Encontrar entrada na tabela UtilizadorRei
                var entry = context.UtilizadorRei.SqlQuery("SELECT * FROM UtilizadorRei WHERE Rei=" + idRei + " AND Utilizador=" + user.Id + ";").ToList();

                if (entry.Count!=0);
                else
                {
                    // Primeir vez na tabela UtilizadorRei para o rei
                    UtilizadorRei uraux = new UtilizadorRei();
                    uraux.Rei = idRei;
                    uraux.Utilizador = (int)user.Id;
                    uraux.Progresso = 0;
                    uraux.Dificuldade = 0;
                    uraux.BioEstado = 1;

                    urDAO.UtilizadorRei.Add(uraux);
                    urDAO.SaveChanges();
                }
                
                Rei r = reiDAO.Rei.Find(idRei);

                var bioestadoatual = urDAO.UtilizadorRei.Where(ur => ur.Utilizador==user.Id && ur.Rei==reiAtual).ToList();

                ReiViewModel rvm;
                if (bioestadoatual.Count > 0)
                {
                    rvm = new ReiViewModel(r, (int)bioestadoatual.ElementAt(0).BioEstado);
                }
                else
                {
                    rvm = new ReiViewModel(r, 1);
                }

                Personalidade.FALA_ATUAL = Personalidade.PERFIL_DO_REI_I; // 1ª Parte da fala
                Personalidade.FALA_ATUAL += r.Nome; // 2ª Parte da fala, o nome do Rei
                Personalidade.FALA_ATUAL += Personalidade.PERFIL_DO_REI_II; // 3ª Parte da fala

                Personalidade.FALA_ATUAL = Personalidade.FALA_ATUAL.Replace("D.", "Dão");

                return View(rvm);
            }
        }


        /*-----------------------------------------------------------------------------------------------
          Items do Perfil do Rei
        ------------------------------------------------------------------------------------------------*/
        public ActionResult ListExercicios()
        {
            List<Exercicio> aux = new List<Exercicio>();

            foreach (Exercicio e in excDAO.Exercicio.ToList())
            {
                if ((int)e.Rei == REI_ATUAL)
                {
                    aux.Add(e);
                }
            }
            ExerciciosViewModel evm = new ExerciciosViewModel(aux,reiDAO,aulaDAO);
            ViewBag.NomeRei = reiDAO.Rei.Find(REI_ATUAL).Nome;

            Personalidade.FALA_ATUAL = Personalidade.getPickExercicio();

            return View(evm);
        }

        /*-----------------------------------------------------------------------------------------------
          Exercícios - Preencher Espaços
        ------------------------------------------------------------------------------------------------*/
        public ActionResult PreencherEspacos(int Id)
        {
            Exercicio e = excDAO.Exercicio.Find(Id);

            pe = new PreencherEspacos(Id);
            pe.parseFich(e.Ficheiro);
            pe.storeKeyWords();
            pe.replaceKeyWords();

            return View(pe);
        }

        [HttpPost]
        public JsonResult PreencherEspacosFim(RespExercicioViewModel values)
        {
            int alineasCorretas = 0;
            int percentagem = 0;

            // Contar nº de respostas certas
            for (int i = 0; i < values.Respostas.Length; i++)
            {
                if (pe.validaResposta(pe.Respostas[i], values.Respostas[i])==true)
                {
                    alineasCorretas++;
                }
            }

            // Calcular percentagem resultante da realização do exercício
            percentagem = (alineasCorretas * 100) / pe.Respostas.Count();
            ViewBag.Score = percentagem;


            var JaFezExercicio = ueDAO.UtilizadorExercicio.Where(usrex => usrex.Exercicio == values.Id && usrex.Utilizador == user.Id);

            if (JaFezExercicio.Count() == 0)
            {
                // O utilizador fez pela primeira vez o exercício portanto o mesmo é contabilizado em termos de progresso
                user.TotalExercicios++;
            }

            user.TotalAlineas += values.Respostas.Count();
            if (percentagem >= 50)
            {
                user.ExerciciosPositivos++; // Incrementar número de exercício positivos
            }
            user.TotalAlineasCorretas += alineasCorretas;

            try
            {
                userDAO.Entry(user).State = System.Data.Entity.EntityState.Modified;
                try
                {
                    UtilizadorExercicio ue = new UtilizadorExercicio();
                    ue.Utilizador = user.Id;
                    ue.Exercicio = values.Id;
                    ue.AlineasCorretas = alineasCorretas;
                    ue.Data = System.DateTime.Now;
                    
                    ueDAO.UtilizadorExercicio.Add(ue);

                    // Se ambas as inserções ocorrerem com sucesso guardo novo estado da base de dados
                    userDAO.SaveChanges();
                    ueDAO.SaveChanges();
                }
                catch (System.Data.SqlClient.SqlException) { }
            }
            catch (System.Data.SqlClient.SqlException) { }

            Personalidade pers = new Personalidade();
            Personalidade.FALA_ATUAL = pers.getFalaResultadoDeExercicio(percentagem);


            string color = "red";
            if (percentagem > 50) { color = "green"; }
            string res = pe.getTextoSolucao() + "<br><br>" + "Resulado: <b style=\"font-size: 24px; color: "+color+";\">" + percentagem + "%</b>";

            return Json(res);
        }

        /*-----------------------------------------------------------------------------------------------*/


        /*-----------------------------------------------------------------------------------------------
          Aulas - Aulas de Texto
        -------------------------------------------------------------------------------------------------*/
        public ActionResult ListAulas()
        {
            List<Aula> aux = new List<Aula>();

            foreach (Aula a in aulaDAO.Aula)
            {
                if ((int)a.Rei == REI_ATUAL)
                {
                    aux.Add(a);
                }
            }
            ViewBag.NomeRei = reiDAO.Rei.Find(REI_ATUAL).Nome;

            Personalidade.FALA_ATUAL = Personalidade.getPickAula();

            return View(aux);
        }

        private static AulaTextoOrganizer atorg;
        private static int paragrafo;
        private static int aulaAtual;
        private static int reiAtual;
        public ActionResult AulaDeTexto(int idAula)
        {
            Aula aula = aulaDAO.Aula.Find(idAula);
            String fonteExtra = fonteDAO.Fonte.Find(idAula).URL; // URL fonte extra relativo à aula
            aulaAtual = idAula;
            reiAtual =(int) aulaDAO.Aula.Find(idAula).Rei;

            paragrafo = 0;
            conta_duvidas = 0;
            atorg = new AulaTextoOrganizer(aulaDAO, reiDAO, uaDAO, fonteExtra, idAula);

            // Primeiro parágrafo
            AulaTextoViewModel atvm = new AulaTextoViewModel();
            atvm.Titulo = atorg.getTituloDaAula();
            atvm.Paragrafo = atorg.getParagrafoAtual(paragrafo);
            atvm.Curiosidade = atorg.getCuriosidadeAtual(paragrafo);
            atvm.Fala = atorg.getFalaAtual(paragrafo);
            atvm.IdAula = idAula;
            atvm.IdRei = reiAtual;
            Personalidade.FALA_ATUAL = atvm.Fala;

            return View(atvm);
        }


        [HttpGet]
        public JsonResult AulaTextoProxParagrafo(int parId)
        {
            string urlExtra = null;
            string tituloPagina = null;

            var end = "FIM";

            if (parId == -2)
            {
                return Json(end, JsonRequestBehavior.AllowGet);
            }
            else
            {
                if (parId == -1)
                {
                    paragrafo++;
                    urlExtra = "NONE";
                }
                else
                {
                    // Paragrafo específico, o utilizador quer tirar uma dúvida

                    // Buscar fonte extra se utilizador está em modo interativo
                    if (user.ModoInt==Const.SUPER_INTERATIVO_ON)
                    {
                        urlExtra = fonteDAO.Fonte.Find(aulaAtual).URL;

                        WebClient x = new WebClient();
                        string source = x.DownloadString(urlExtra);

                        tituloPagina = Regex.Match(source, @"\<title\b[^>]*\>\s*(?<Title>[\s\S]*?)\</title\>", RegexOptions.IgnoreCase).Groups["Title"].Value;
                    }
                    else
                    {
                        urlExtra = "NONE";
                    }
                }

                int parGet;
                if (parId != -1 && parId != -2) { parGet = parId; }
                else parGet = paragrafo;

                var r = new
                {
                    Titulo = "FIM_AULA",
                    Paragrafo = "FIM_AULA",
                    Curiosidade = "FIM_AULA",
                    Fala = "FIM_AULA",
                    FonteExtra = "FIM_AULA"
                };
                if (parGet < atorg.paragrafos.Count)
                {
                    // Paragrafo seguinte
                    AulaTextoViewModel atvm = new AulaTextoViewModel(atorg.getTituloDaAula(), atorg.getParagrafoAtual(parGet),
                        atorg.getCuriosidadeAtual(parGet), atorg.getFalaAtual(parGet), aulaAtual, reiAtual);
                    Personalidade.FALA_ATUAL = atvm.Fala;
                    if (parId != -1 && urlExtra != null)
                    {
                        Personalidade.FALA_ATUAL += ". Se tens mais dúvidas tenta a aula de vídeo.";
                        if (user.ModoInt == Const.SUPER_INTERATIVO_ON)
                        {
                            if (tituloPagina == null)
                            {
                                Personalidade.FALA_ATUAL += ". Se quiseres saber mais sobre esta parte da matéria podes consultar esta página.";
                            }
                            else
                            {
                                Personalidade.FALA_ATUAL += ". Se quiseres saber mais sobre esta parte da matéria podes consultar a página, "+tituloPagina;
                            }
                        }
                    }

                    // Escrever por cima do pacote de término de Aula
                    r = new
                    {
                        Titulo = atvm.Titulo,
                        Paragrafo = atvm.Paragrafo,
                        Curiosidade = atvm.Curiosidade,
                        Fala = atvm.Fala,
                        FonteExtra = urlExtra
                    };
                }

                return Json(r, JsonRequestBehavior.AllowGet);
            }
        }

        [HttpPost]
        public ActionResult TerminarAulaTexto(int parId)
        {
            // Aula terminou

            // Tutor informa utilizador que aula terminou
            Personalidade.FALA_ATUAL = Personalidade.FIM_DE_AULA;

            // Incrementar bioestado em UtilizadorRei
            try
            {
                var aulaentry = uaDAO.UtilizadorAula.Where(uaula => uaula.Aula == aulaAtual && uaula.Utilizador == user.Id).ToList();
                if (aulaentry.Count == 0)
                {
                    var urentry = urDAO.UtilizadorRei.Where(ur => ur.Utilizador==user.Id && ur.Rei==reiAtual).ToList();
                    urentry.ForEach(ur => ur.BioEstado += 1);

                    urDAO.SaveChanges();
                }
            }
            catch (System.Data.SqlClient.SqlException) { }

            // Incrementar total de Aulas a que este utilizador assistiu
            try
            {
                // Apenas contabilizar aula assistida se o utilizador assiste pela primeira vez
                var aulaentry = uaDAO.UtilizadorAula.Where(uaula => uaula.Aula == aulaAtual && uaula.Utilizador == user.Id).ToList();
                if (aulaentry.Count == 0)
                {
                    if (userDAO.Utilizador.Find(user.Id) != null)
                    {
                        var urentry = userDAO.Utilizador.Where(usr => usr.Id == user.Id).ToList();
                        urentry.ForEach(usr => usr.TotalAulas += 1);

                        userDAO.SaveChanges();
                    }
                }
            }
            catch (System.Data.SqlClient.SqlException) { }

            // Acrescentar registo de aula em UtilizadorAula
            DateTime now = DateTime.Now;
            UtilizadorAula ua = new UtilizadorAula();

            ua.Data = now;
            ua.Aula = aulaAtual;
            ua.Utilizador = user.Id;

            try
            {
                uaDAO.UtilizadorAula.Add(ua);
                uaDAO.SaveChanges();
            } catch (System.Data.SqlClient.SqlException) { }

            // Redirecionar para página de perfil do Rei correspondete à aula que terminou
            return RedirectToAction("PerfilDoRei", new { idRei = reiAtual });
        }

        private static int conta_duvidas = 0;

        [HttpPost]
        public JsonResult FalaDuvidaStandardAulaTexto()
        {
            if (conta_duvidas < 2)
            {
                Personalidade.FALA_ATUAL = Personalidade.getDuvida();
            }
            else
            {
                Personalidade.FALA_ATUAL = Personalidade.MUITAS_DUVIDAS;
            }

            conta_duvidas++;

            return Json("OK", JsonRequestBehavior.AllowGet);
        }


        /*-----------------------------------------------------------------------------------------------
          Aulas - Aulas de Vídeo
        -------------------------------------------------------------------------------------------------*/
        public ActionResult AulaDeVideo(int idAula)
        {
            Aula aula = aulaDAO.Aula.Find(idAula);
            AulaVideoViewModel avm = new AulaVideoViewModel(aula, pergDAO, (int)user.ModoInt, new Personalidade(user.Nome, (int)user.Idade));

            return View(avm);
        }



        // (  .  .  .  )
        //public JsonResult UpdateQuestionVideo(){
        //    // Se não há mais perguntas a fazer terminar aula aqui reencaminhar vista para perfil do rei
        //    // Muralhas diz: já podes fazer mais exercícios

        //    // 1 - Buscar próxima pergunta
        //    // 2 - Instanciar AulaVideoViewModel com os dados da nova pergunta lá dentro
        //    // 3 - Mandar o pacote como JsonResul allow Get

        //    //var c = post.Comment
        //    //    .Select(x => new CommentsViewModel(x))
        //    //    .ToList();
        //    //return Json(c, JsonRequestBehavior.AllowGet);

        //    PerguntaIndex++;

        //    //var c = new AulaVideoViewModel(AulaTitulo, TimeStampsIndexados[PerguntaIndex], QuestoesPorOrdemTempo[PerguntaIndex],
        //    //RespostasPorOrdemTempo[PerguntaIndex], (int)user.ModoInt, FontesExtraPorOrdemTempo[PerguntaIndex]);
        //    Personalidade.FALA_ATUAL = QuestoesPorOrdemTempo[PerguntaIndex];

        //    AulaVideoViewModel c = new AulaVideoViewModel
        //    {
        //        TituloAula = AulaTitulo,
        //        TimeStamp = TimeStampsIndexados[PerguntaIndex],
        //        Pergunta = QuestoesPorOrdemTempo[PerguntaIndex],
        //        Resposta = RespostasPorOrdemTempo[PerguntaIndex],
        //        ModoInteratividade = (int)user.ModoInt,
        //        FonteExtra = FontesExtraPorOrdemTempo[PerguntaIndex]
        //    };

        //    return Json(c, JsonRequestBehavior.AllowGet);
        //}
        /*-----------------------------------------------------------------------------------------------*/
    }
}