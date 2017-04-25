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

namespace Meter_Replacement
{
    [Activity(Label = "Menu", Icon = "@drawable/icon")]
    class MenuActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            Toast.MakeText(this, "Logged in successfully", ToastLength.Long).Show();
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Menu);
            var btn = FindViewById(Resource.Id.button1);
            var btnCamera = FindViewById(Resource.Id.button2);
            var buttonScan = FindViewById(Resource.Id.button3);
            var buttonList = FindViewById(Resource.Id.button4);
            // SetContentView (Resource.Layout.Main);
            MobileBarcodeScanner.Initialize(Application);
            btn.Click += (s, e) => {
                Intent nextActivity = new Intent(this, typeof(ReplacementDataActivity));
                StartActivity(nextActivity);
            };
            btnCamera.Click += delegate {
                StartActivity(typeof(CameraActivity));
            };
            buttonScan.Click += async (sender, e) =>
            {

                var scanner = new ZXing.Mobile.MobileBarcodeScanner();
                var result = await scanner.Scan();

                if (result != null)
                    Console.WriteLine("Scanned Barcode: " + result.Text);
            };
            buttonList.Click += delegate {
                StartActivity(typeof(ListActivity));
            };
        }
    }
}