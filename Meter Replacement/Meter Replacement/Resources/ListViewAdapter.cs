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
using Meter_Replacement.Resources.Model;
using Java.Lang;

namespace Meter_Replacement.Resources
{
    public class ViewHolder : Java.Lang.Object
    {
        public TextView address { get; set; }
        public TextView serialNumber { get; set; }
        public TextView consumption { get; set; }
    }
    public class ListViewAdapter : BaseAdapter
    {
        private Activity activity;
        private List<Watermeter> lstWatermeter;
        public ListViewAdapter(Activity activity, List<Watermeter> lstWatermeter)
        {
            this.activity = activity;
            this.lstWatermeter = lstWatermeter;
        }

        public override int Count
        {
            get
            {
                return lstWatermeter.Count;
            }
        }

        public override Java.Lang.Object GetItem(int position)
        {
            return null;
        }

        public override long GetItemId(int position)
        {
            return lstWatermeter[position].replacementID;
        }

        public override View GetView(int position, View convertView, ViewGroup parent)
        {
            var view = convertView ?? activity.LayoutInflater.Inflate(Resource.Layout.list_view_dataTemplate, parent, false);

            var address = view.FindViewById<TextView>(Resource.Id.textViewList1);
            var serialNumber = view.FindViewById<TextView>(Resource.Id.textViewList2);
            var consumption = view.FindViewById<TextView>(Resource.Id.textViewList3);

            //address.Text = lstWatermeter[position].address;
            //serialNumber.Text = "" + lstWatermeter[position].serialNumber;
            //consumption.Text = "" + lstWatermeter[position].consumption;
            address.Text = "test";
            serialNumber.Text = "test2";
            consumption.Text = "test3";

            return view;
        }
    }
}