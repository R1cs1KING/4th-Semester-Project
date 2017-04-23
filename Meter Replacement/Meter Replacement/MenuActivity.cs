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

namespace Meter_Replacement
{
    [Activity(Label = "Menu", Icon = "@drawable/icon")]
    class MenuActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Menu);
            Toast.MakeText(this, "Log in successful", ToastLength.Long).Show();
            // SetContentView (Resource.Layout.Main);
        }
    }
}