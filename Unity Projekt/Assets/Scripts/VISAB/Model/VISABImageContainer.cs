using System.Collections.Generic;
using VISABConnector;

namespace Assets.Scripts.VISAB
{
    public class VISABImageContainer : IImageContainer
    {
        public byte[] MapImage { get; set; }

        public byte[] CityImage { get; set; }
        public string CityAnnotation { get; set; }
        public byte[] StreetImage { get; set; }
        public string StreetAnnotation { get; set; }
        public byte[] VillageImage { get; set; }
        public string VillageAnnotation { get; set; }

    }
}