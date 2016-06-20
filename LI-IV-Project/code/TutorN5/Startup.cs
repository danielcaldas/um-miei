using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(TutorN5.Startup))]
namespace TutorN5
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
