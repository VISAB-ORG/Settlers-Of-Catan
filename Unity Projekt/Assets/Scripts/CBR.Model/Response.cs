using System.Runtime.Serialization;

namespace Assets.Scripts.CBR.Model
{
    /**
     * Diese Klasse bietet eine Datenstruktur, um eine Antwort (Response) darstellen zu können.
     */
    [DataContract]
    public class Response
    {
        /**
         * Eine Antwort besteht aus der Situation.
         */
        [DataMember]
        public Situation situation { get; set; }
        /**
         * Eine Antwort muss den Plan des Spielers enthalten
         */
        [DataMember]
        public Plan.Plan plan { get; set; }

        /**
         * Default-Konstruktor.
         */
        public Response() : this(new Situation(), new Plan.Plan())
        {

        }
        /**
         * Konstruktor, der eine Situation als Parameter erwartet.
         */
        public Response(Situation situation) : this(situation, new Plan.Plan())
        {

        }

        /**
         * Konstruktor, der eine Situation und einen Plan als Parameter erwartet.
         */
        public Response(Situation situation, Plan.Plan plan)
        {
            this.situation = situation;
            this.plan = plan;
        }



        public override string ToString()
        {
            return "Response: " + situation.ToString() + "Plan: " + plan.ToString();
        }

    }
}
