using Android.App;
using Android.Widget;
using Android.OS;
using Android.Content;

namespace Meter_Replacement
{
    [Activity(Label = "Meter_Replacement", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);
            var btn = FindViewById(Resource.Id.button1);
            // SetContentView (Resource.Layout.Main);

            /*btn.Click += (s, e) => {
                Intent nextActivity = new Intent(this, typeof(MenuActivity));
                StartActivity(nextActivity);
            };*/
            btn.Click += delegate {
                StartActivity(typeof(MenuActivity));
            };
        }
    }
}

