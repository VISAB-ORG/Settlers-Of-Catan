using Assets.Scripts.Model;
using System.Runtime.Serialization;
namespace Assets.Scripts.CBR.Plan
{
    /**
     * Klasse, die die Aktion zum Würfeln darstellt
     */
    [DataContract]
    public class RollDice : Action
    {
        public RollDice() : base("RollDice")
        {
        }
    }
}
