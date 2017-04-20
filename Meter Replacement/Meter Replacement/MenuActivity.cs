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
    [Activity(Label = "Menu", MainLauncher = true, Icon = "@drawable/icon")]
    class MenuActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Menu);
            // SetContentView (Resource.Layout.Main);
        }
    }
}