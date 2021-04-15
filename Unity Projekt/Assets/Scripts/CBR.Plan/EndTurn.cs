using Assets.Scripts.Model;
using System.Runtime.Serialization;
namespace Assets.Scripts.CBR.Plan
{
    /**
     * Klasse, die die Aktion den Zug zu beenden darstellt
     */
    [DataContract]
    public class EndTurn : Action
    {
        public EndTurn() : base("EndTurn")
        {
        }
    }
}
