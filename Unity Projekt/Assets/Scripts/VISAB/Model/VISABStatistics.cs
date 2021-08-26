using Assets.Scripts.VISAB.Model;
using System.Collections.Generic;
using VISABConnector;

namespace Assets.Scripts.VISAB.Model
{
    public class VISABStatistics : IStatistics
    {
        public int Turn { get; set; }

        public string TurnTimeStamp { get; set; }

        public IList<PlayerInformation> Players { get; set; } = new List<PlayerInformation>();

        public int DiceNumberRolled { get; set; }
    }
}