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
using Java.Lang;
using Meter_Replacement.Model;
using Android;

namespace PickFood
{
    class ListViewAdapter : BaseAdapter
    {
        Activity activity;
        List<Food> lstAccounts;
        LayoutInflater inflater;
       

        public ListViewAdapter(Activity activity, List<Food> lstAccounts)
        {
            this.activity = activity;
            this.lstAccounts = lstAccounts;
        }

        public override int Count
        {
            get
            {
                return lstAccounts.Count;
            }
        }

        public override Java.Lang.Object GetItem(int position)
        {
            return position;
        }

        public override long GetItemId(int position)
        {
            return position;
        }

        public override View GetView(int position, View convertView, ViewGroup parent)
        {
            inflater = (LayoutInflater)activity.BaseContext.GetSystemService(Context.LayoutInflaterService);
            View itemView = inflater.Inflate(Resource.Layout.List_Item, null);

            TextView txtName = itemView.FindViewById<TextView>(Resource.Id.list_name);
            TextView txtDescription = itemView.FindViewById<TextView>(Resource.Id.list_description);
            TextView txtLocation = itemView.FindViewById<TextView>(Resource.Id.list_location);

            if (lstAccounts.Count > 0)
            {
                txtName.Text = lstAccounts[position].name;
                txtDescription.Text = lstAccounts[position].description;
                txtLocation.Text = lstAccounts[position].location;
            }
            return itemView;
        }
    }
    
}
