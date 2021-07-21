using System.Collections.Generic;

namespace Assets.Scripts.VISAB
{
    public class VISABImageContainer
    {
        public IDictionary<string, byte[]> StaticObjects { get; set; } = new Dictionary<string, byte[]>();
        public IDictionary<string, byte[]> MoveableObjects { get; set; } = new Dictionary<string, byte[]>();
    }
}