using System.Runtime.Serialization;
namespace Assets.Scripts.CBR.Plan
{
    /**
     * Diese abstrakte Klasse ist die Oberklasse aller konkreten Aktionen
     */
    [DataContract]
    public abstract class Action
    {

        /**
	     * Attribut das den Namen der Aktion darstellt.
	     */
        [DataMember]
        public string name { get; set; }

        /**
         * Konstruktor der den Namen erwartet.
         */
        public Action(string name) 
        {
            this.name = name;
        }

    }
}
