using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.Threading.Tasks;
using Microsoft.Speech.Synthesis;
using System.Globalization;

namespace TutorN5.Models.tutor
{
    public class Speaker
    {
        private static Speaker instancia = null;
        private static object objLock = new object();

        public int Volume { get; set; }
        public int Velocidade { get; set; }

        private SpeechSynthesizer SpeechEngine = new SpeechSynthesizer();
        public void speakText(string strTexto)
        {
            SpeechEngine.Rate = this.Velocidade;
            SpeechEngine.Volume = this.Volume;

            if (!string.IsNullOrWhiteSpace(strTexto))
            {
                switch (SpeechEngine.State)
                {
                    case SynthesizerState.Ready:
                        SpeechEngine.SetOutputToDefaultAudioDevice();
                        SpeechEngine.SpeakAsync(strTexto);
                        break;
                    case SynthesizerState.Speaking:
                        SpeechEngine.Pause();
                        break;
                    default:
                        SpeechEngine.Dispose();
                        break;
                }
            }
        }

        public static Speaker getInstance()
        {
            lock (objLock)
            {
                if (instancia == null)
                {
                    instancia = new Speaker();
                }

                return instancia;
            }
        }

        public int randomInt()
        {
            Random r = new Random();
            int x = r.Next(0, 101);
            return x;
        }
    }
}