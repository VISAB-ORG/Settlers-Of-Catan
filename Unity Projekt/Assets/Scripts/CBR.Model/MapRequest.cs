using System.Runtime.Serialization;
using Assets.Scripts.Model;

namespace Assets.Scripts.CBR.Model
{
    [DataContract]
    public class MapRequest
    {
        [DataMember]
        public Map map { get; set; }

        /**
         * Default-Konstruktor
         */
        public MapRequest() : this(new Map())
        {

        }

        public MapRequest(Map map)
        {
            this.map = map;
        }

        public override string ToString()
        {
            return "Request: " + map.ToString();
        }
    }
}
