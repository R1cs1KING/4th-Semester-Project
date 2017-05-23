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

namespace PickFood
{
    
    [Activity(Label = "Menu", Icon = "@drawable/icon")]
    class MenuActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            Toast.MakeText(this, "Logged in successfully", ToastLength.Long).Show();
          
             SetContentView(Resource.Layout.Menu);
             var btn = FindViewById(Resource.Id.button1);
             var btnCamera = FindViewById(Resource.Id.button2);
             var buttonList = FindViewById(Resource.Id.button4);
        
       
              btnCamera.Click += delegate {
                    StartActivity(typeof(CameraActivity));
                };
               
                buttonList.Click += delegate {
                    StartActivity(typeof(AddItem));
                };
                
        }

    }
    
}