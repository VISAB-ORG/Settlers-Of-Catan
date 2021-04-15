using Assets.Scripts.Model;
using System.Runtime.Serialization;

namespace Assets.Scripts.CBR.Plan
{
    /**
     * Klasse, die die Aktion die Bauplätze für Straßen zu aktivieren oder zu deaktiviren darstellt
     */
    [DataContract]
    public class ActivateRoadPlaces : Action
    {
        public ActivateRoadPlaces() : base("ActivateRoadPlaces")
        {
        }
    }
}
