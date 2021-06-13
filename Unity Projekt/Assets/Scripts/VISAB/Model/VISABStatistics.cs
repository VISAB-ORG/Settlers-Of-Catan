using VISABConnector;

namespace Assets.Scripts.VISAB
{
    public class VISABStatistics : IVISABStatistics
    {
        public PlayerInformation Player1 { get; set; }

        public PlayerInformation Player2 { get; set; }

        public int Turn { get; set; }

        public string TurnTimeStamp { get; set; }

        public string Game => "Settlers";
    }
}