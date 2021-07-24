using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using VISABConnector;

namespace Assets.Scripts.VISAB.Model
{
    public class VISABMetaInformation : IMetaInformation
    {
        public string Game => "Settlers";

        public int PlayerCount => PlayerInformation.Count;

        public IDictionary<string, string> PlayerInformation { get; set; } = new Dictionary<string, string>();
        public IDictionary<string, string> PlayerColors { get; set; } = new Dictionary<string, string>();

        public MapRectangle MapRectangle { get; set; }
    }
}
