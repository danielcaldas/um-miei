using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TutorN5.Models.reis;


namespace TutorN5.Models.utilizador
{
    public class PerfilUtilizadorViewModel
    {
        public List<Rei> reis { get; set; }
        public PerfilUtilizadorViewModel(ReiDAO rDAO)
        {
            reis = new List<Rei>(reis = rDAO.Rei.ToList());
        }
    }
}