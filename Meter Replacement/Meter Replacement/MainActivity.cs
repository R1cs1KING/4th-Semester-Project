using Android.App;
using Android.Widget;
using Android.OS;
using Android.Content;
using Android.Support.V7.App;
using Firebase;
using Firebase.Auth;
using static Android.Views.View;
using Android.Views;
using Android.Gms.Tasks;
using System;
using Android.Support.Design.Widget;

namespace Meter_Replacement
{
    [Activity(Label = "Meter Replacement", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity, IOnClickListener, IOnCompleteListener
    {
        Button btnLogin;
        EditText input_ID;
        TextView textView2;
        EditText input_Password;
        LinearLayout activity_main;
        public static FirebaseApp app;
        FirebaseAuth auth;
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            InitFirebaseAuth();

            SetContentView(Resource.Layout.Main);
            activity_main = FindViewById<LinearLayout>(Resource.Id.activity_main);
            textView2 = FindViewById<TextView>(Resource.Id.textView2);
            btnLogin = FindViewById<Button>(Resource.Id.button1);
            input_ID = FindViewById<EditText>(Resource.Id.editText1);
            input_Password = FindViewById<EditText>(Resource.Id.editText2);

            btnLogin.Click += (s, e) => {
                if (input_ID.Text != "" || input_Password.Text != "")
                {
                    LoginUser(input_ID.Text, input_Password.Text);
                }
                else
                {
                    Toast.MakeText(this, "Please enter email and password", ToastLength.Long).Show();
                }
            };
        }

         private void InitFirebaseAuth()
          {
               var options = new FirebaseOptions.Builder()
               .SetApplicationId("1:321577095986:android:f152c118893f1239")
               .SetApiKey("AIzaSyBOhneF6dGRDom7W4DNAnNB59Aw1Pigy-o")
               .Build();
        
               if (app == null)
                   app = FirebaseApp.InitializeApp(this, options);
           auth = FirebaseAuth.GetInstance(app);
             }

          private void LoginUser(string email, string password)
           {
             auth.SignInWithEmailAndPassword(email, password)
                  .AddOnCompleteListener(this);
           }

        public void OnClick(View v)
        {
            throw new NotImplementedException();
        }

          public void OnComplete(Task task)
          {
              if (task.IsSuccessful)
              {
                  StartActivity(new Android.Content.Intent(this, typeof(MenuActivity)));
                  Finish();
              }
              else
              {
                Toast.MakeText(this, "Login failed", ToastLength.Long).Show();
              }
          }
          
    }
}

