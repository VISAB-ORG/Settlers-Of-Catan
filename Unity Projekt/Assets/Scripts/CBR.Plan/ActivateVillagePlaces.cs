using Assets.Scripts.Model;
using System.Runtime.Serialization;
namespace Assets.Scripts.CBR.Plan
{
    /**
     * Klasse, die die Aktion die Bauplätze für Sieldungen zu aktivieren oder zu deaktiviren darstellt
     */
    [DataContract]
    public class ActivateVillagePlaces : Action
    {

        public ActivateVillagePlaces() : base("ActivateVillagePlaces")
        {
        }
    }
}
