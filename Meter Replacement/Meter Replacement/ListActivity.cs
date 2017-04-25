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
    public class ListActivity : Activity
    {
        ListView lstData;
        List<Watermeter> lstSource = new List<Watermeter>();
        DataBase db;
        protected override void OnCreate(Bundle bundle)
        {

            base.OnCreate(bundle);
            SetContentView(Resource.Layout.ReplacementList);
            lstData = FindViewById<ListView>(Resource.Id.listView);

            db = new DataBase();
            db.createDataBase();
            string folder = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            Log.Info("DB_PATH", folder);
          
            // Set our view from the "main" layout resource
            
            var buttonFetch = FindViewById(Resource.Id.buttonList);
            //SetContentView (Resource.Layout.Main);

            ///////////////////////////////////////////////////

            ///////////////////////////////////////////////////
            buttonFetch.Click += delegate
            {
                Watermeter watermeter = new Watermeter()
                {
                    address = "lol1",
                    serialNumber = "2",
                    comments = "tesssst",
                    consumption = 3,
                    replacementID = 1323
                };
                db.insertIntoTableWatermeter(watermeter);
                LoadData();
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