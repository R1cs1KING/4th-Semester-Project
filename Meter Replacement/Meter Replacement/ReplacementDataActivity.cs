using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using ZXing.Mobile;
using Android.Util;
using Meter_Replacement.Resources.Model;
using Meter_Replacement.Resources.DataHelper;
using Meter_Replacement.Resources;

namespace Meter_Replacement
{
    [Activity(Label = "Report", Icon = "@drawable/icon")]
    public class ReplacementDataActivity : Activity
    {
        ListView lstData;
        List<Watermeter> lstSource = new List<Watermeter>();
        DataBase db;
        TextView serialNumber;
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            //Create DataBase
            db = new DataBase();
            db.createDataBase();
            string folder = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            Log.Info("DB_PATH", folder);
            lstData = FindViewById<ListView>(Resource.Id.listView);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.ReplacementData);
            var buttonScan = FindViewById(Resource.Id.button1);
            var buttonAdd = FindViewById(Resource.Id.button2);

            // SetContentView (Resource.Layout.Main);
            ///////////////////////////////////////////////////
            var edtAddress = FindViewById<EditText>(Resource.Id.editText1);
            serialNumber = FindViewById<TextView>(Resource.Id.editText2);
            var edtConsumption = FindViewById<EditText>(Resource.Id.editText3);
            var edtReplacement = FindViewById<EditText>(Resource.Id.editText4);
            var edtComments = FindViewById<EditText>(Resource.Id.editText5);
            ///////////////////////////////////////////////////
            
            ///////////////////////////////////////////////////
            buttonScan.Click += async (sender, e) =>
            {
                var scanner = new ZXing.Mobile.MobileBarcodeScanner();
                var result = await scanner.Scan();

                if (result != null)
                {
                    Console.WriteLine("Scanned Barcode: " + result.Text);
                    serialNumber.Text = result.Text;
                }
            };
            buttonAdd.Click += delegate
            {
                Watermeter watermeter = new Watermeter()
                {
                    address = edtAddress.Text,
                    serialNumber = serialNumber.Text,
                    consumption = int.Parse(edtConsumption.Text),
                    replacementID = int.Parse(edtReplacement.Text),
                    comments = edtComments.Text
                };
                db.insertIntoTableWatermeter(watermeter);
                Toast.MakeText(this, "Successfully added", ToastLength.Long).Show();
            };

        }

        private void LoadData()
        {
            lstSource = db.selectTableWatermeter();
            var adapter = new ListViewAdapter(this, lstSource);
            lstData.Adapter = adapter;
        }

    }
}