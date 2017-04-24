using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SQLite;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Meter_Replacement.Resources.Model
{
    public class Watermeter
    {
        [PrimaryKey, AutoIncrement]
        public string address { get; set; }
        public string serialNumber { get; set; }
        public int consumption { get; set; }
        public int replacementID { get; set; }
        public string comments { get; set; }
    }
}