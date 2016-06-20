using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TutorN5.Models.reis.aprendizagem.aulas;

namespace TutorN5.Models.reis.aprendizagem.exercicios
{
    public class ExerciciosViewModel
    {
        public List<ExercicioView> exercicios { get; set; }

        private AulaDAO aDAO { get; set; }
        private ReiDAO kingDAO { get; set; }


        public ExerciciosViewModel(List<Exercicio> exs, ReiDAO r, AulaDAO a)
        {
            exercicios = new List<ExercicioView>();
            aDAO = a;
            kingDAO = r;

            foreach (Exercicio e in exs)
            {
                String tipo = getNomeTipoExercicio(e);
                exercicios.Add(new ExercicioView(e.Id, e.Dificuldade, tipo, getNomeRei(e.Rei), getTituloAula(e.Aula)));
            }
        }

        public String getNomeTipoExercicio(Exercicio e)
        {
            if(e.Tipo.CompareTo(Const.TAG_PREENCHER_ESPACOS)==0){
                return Const.DISPLAY_PREENCHER_ESPACOS;
            }
            else
            {
                return "sem tipo";
            }
        }

        public String getNomeRei(int idRei)
        {
            return kingDAO.Rei.Find(idRei).Nome;
        }

        public String getTituloAula(int idAula)
        {
            return aDAO.Aula.Find(idAula).Titulo;
        }
    }

    public class ExercicioView
    {
        public int Id { get; set; }
        public String Dificuldade { get; set; }
        public string Tipo { get; set; }
        public string Rei { get; set; }
        public string Aula { get; set; }
        public ExercicioView(int id, int d, String tipo, String r, String a) 
        {
            this.Id = id;

            switch (d)
            {
                case 1:
                    this.Dificuldade = "Fácil";
                    break;
                case 2:
                    this.Dificuldade = "Médio";
                    break;
                case 3:
                    this.Dificuldade = "Difícil";
                    break;
            }

            this.Tipo = tipo;
            this.Rei = r;
            this.Aula = a;
        }
    }
}