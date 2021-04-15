using System;
using System.Net.Sockets;
using System.IO;
using Assets.Scripts.Utility;
using Assets.Scripts.CBR.Model;
using System.Threading;
using System.Text;
using System.Diagnostics;

namespace Assets.Scripts.Connection
{

    /**
     * Klasse stellt die Verbindung via TCP/IP zum Java Projekt her.
     */
    public class Connection
    {

        /**
         * TCP-Client
         */
        private TcpClient mClient;
        /**
         * Data Stream.
         */
        private Stream mStream;

        /**
         * Diese Methode stellt die Verbindung her.
         */
        private void InitiateConnection()
        {
            mClient = new TcpClient();
            mClient.Connect(Constants.HOST_ADDRESS, Constants.PORT);
            mStream = mClient.GetStream();
            UnityEngine.Debug.Log(mStream);
        }

        ~Connection()
        {
            CloseConnection();
        }

        /**
         * Diese Methode schließt die Verbindung zwischen C# und Java.
         */
        private void CloseConnection()
        {
            if (mClient != null && mClient.Connected)
            {
                Console.WriteLine("Shutting down TCP/IP");
                mClient.Close();
            }
        }

        /**
         * Methode zum Senden einer Anfrage an den Server
         */
        public Response Send(Request request)
        {

            InitiateConnection(); //Verbindung herstellen


            string json = JsonParser<Request>.SerializeObject(request) + Environment.NewLine; //Anfrage in Json übersetzen
            //UnityEngine.Debug.Log("Request is: " + json);
            ASCIIEncoding asen = new ASCIIEncoding();
            byte[] data = asen.GetBytes(json);

            mStream.Write(data, 0, data.Length); //Anfrage senden

            Thread.Sleep(100); //Thread schlafen lassen, um sicher zu gehen, dass die Anfrage vom Server beantwortet wurde

            //Lesen der Antwort
            byte[] responseData = new byte[16384];
            string textReceived = "";
            int read = 0;
            do
            {
                read = mStream.Read(responseData, 0, responseData.Length);
                for (int i = 0; i < read; i++)
                {
                    textReceived += (char)responseData[i];
                }
            } while (read > 0);

            //UnityEngine.Debug.Log("Answer is: " + textReceived); //Ausgabe der Antwort

            Response response = JsonParser<Response>.DeserializeObject(textReceived); //Antwort aus dem Json-Format in ein C#-Objekt übersetzen

            CloseConnection(); //Verbindung schließen

            return response;
        }

    }

}
