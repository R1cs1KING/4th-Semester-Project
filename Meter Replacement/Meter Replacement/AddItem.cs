using Android.App;
using Android.Widget;
using Android.OS;
using Android.Support.V7.App;
using Android.Views;
using Meter_Replacement.Model;
using System.Collections.Generic;
using System;
using System.Threading.Tasks;
using Firebase.Xamarin.Database;
using Firebase.Xamarin.Database.Query;
using Firebase.Xamarin.Auth;
using Android;

namespace PickFood
{
    [Activity(Label = "Add offer", Icon = "@drawable/icon", Theme = "@style/AppTheme")]
    public class AddItem : AppCompatActivity
    {
        
        private EditText input_name, input_description, input_location;
        private ListView list_data;
        private ProgressBar circular_progress;

        private List<Food> list_foods = new List<Food>();
        private ListViewAdapter adapter;
        private Food SelectedFood;

    
        private const string FirebaseURL = "https://pickfood-5c351.firebaseio.com/";
        protected async override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
              SetContentView (Resource.Layout.AddItem);

       
            Android.Support.V7.Widget.Toolbar toolbar = FindViewById<Android.Support.V7.Widget.Toolbar>(Resource.Id.toolbar);
            toolbar.Title = "PickFood Xamarin";
            SetSupportActionBar(toolbar);

            
            
            circular_progress = FindViewById<ProgressBar>(Resource.Id.circularProgress);
            input_name = FindViewById<EditText>(Resource.Id.name);
            input_description = FindViewById<EditText>(Resource.Id.description);
            input_location = FindViewById<EditText>(Resource.Id.location);
            list_data = FindViewById<ListView>(Resource.Id.list_data);
            list_data.ItemClick += (s, e) => {
                Food food = list_foods[e.Position];
                SelectedFood = food;
                input_name.Text = food.name;
                input_description.Text = food.description;
                input_location.Text = food.location;
            };
            

              await LoadData();


              }

             private async Task LoadData()
             {
                 circular_progress.Visibility = ViewStates.Visible;
                 list_data.Visibility = ViewStates.Invisible;

                 var firebase = new FirebaseClient(FirebaseURL);
                 var items = await firebase
                     .Child("FoodXamarin")
                     .OnceAsync<Food>();

                 list_foods.Clear();
                 adapter = null;
                 foreach(var item in items)
                 {
                     Food food = new Food();
                     food.name = item.Object.name;
                     food.description = item.Object.description;
                     food.location = item.Object.location;

                     list_foods.Add(food);
                 }
                 adapter = new ListViewAdapter(this, list_foods);
                 adapter.NotifyDataSetChanged();
                 list_data.Adapter = adapter;

                 circular_progress.Visibility = ViewStates.Invisible;
                 list_data.Visibility = ViewStates.Visible;

             }

             public override bool OnCreateOptionsMenu(IMenu menu)
             {
                 MenuInflater.Inflate(Resource.Menu.menu_main, menu);
                 return base.OnCreateOptionsMenu(menu);
             }

             public override bool OnOptionsItemSelected(IMenuItem item)
             {
                 int id = item.ItemId;
                 if(id == Resource.Id.menu_add)
                 {
                     CreateFood();
                 }
                 else if (id == Resource.Id.menu_save) // Update
                 {
                     UpdateFood(SelectedFood.name, input_description.Text, input_location.Text);
                 }
                 else if (id == Resource.Id.menu_remove)
                 {
                     DeleteFood(SelectedFood.name);
                 }
                 return base.OnOptionsItemSelected(item);
             }

             private async void DeleteFood(string uid)
             {
                 var firebase = new FirebaseClient(FirebaseURL);
                 await firebase.Child("FoodXamarin").Child(uid).DeleteAsync();
                 await LoadData();
             }

             private async void UpdateFood(string name, string description, string location)
             {
                 var firebase = new FirebaseClient(FirebaseURL);
                 await firebase.Child("FoodXamarin").Child(name).Child("Name").PutAsync(name);
                 await firebase.Child("FoodXamarin").Child(name).Child("Description").PutAsync(description);
                 await firebase.Child("FoodXamarin").Child(name).Child("Location").PutAsync(location);

                 await LoadData();
             }

             private async void CreateFood()
             {
                 Food food = new Food();
                 food.name = input_name.Text;
                 food.description = input_description.Text;
                 food.location = input_location.Text;

                 var firebase = new FirebaseClient(FirebaseURL);

                 //Add item
                 var item = await firebase.Child("FoodXamarin").PostAsync<Food>(food);

                 await LoadData();
             }
             
        }

    }

    
