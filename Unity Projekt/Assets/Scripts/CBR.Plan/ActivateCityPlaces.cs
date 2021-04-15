using Assets.Scripts.Model;
using System.Runtime.Serialization;
namespace Assets.Scripts.CBR.Plan
{
    /**
     * Klasse, die die Aktion die Bauplätze für Städte zu aktivieren oder zu deaktiviren darstellt
     */
    [DataContract]
    public class ActivateCityPlaces : Action
    {
        public ActivateCityPlaces() : base("ActivateCityPlaces")
        {
        }
    }
}
