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
    [Activity(Label = "Report", Icon = "@drawable/icon")]
    public class ReplacementDataActivity : Activity
    {
        TextView serialNumber;
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.ReplacementData);
            var buttonScan = FindViewById(Resource.Id.button1);
            serialNumber = FindViewById<TextView>(Resource.Id.editText2);
            // SetContentView (Resource.Layout.Main);
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
        }
    }
}