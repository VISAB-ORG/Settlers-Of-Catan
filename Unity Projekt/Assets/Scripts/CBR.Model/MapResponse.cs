using System.Runtime.Serialization;

namespace Assets.Scripts.CBR.Model
{ 
    [DataContract]
    public class MapResponse
    {
        [DataMember]
        public string mapAns { get; set; }

        /**
         * Default-Konstruktor
         */
        public MapResponse() : this("")
        {

        }

        public MapResponse(string ans)
        {
            this.mapAns = ans;
        }

        public override string ToString()
        {
            return "Response: " + mapAns;
        }
    }
}
