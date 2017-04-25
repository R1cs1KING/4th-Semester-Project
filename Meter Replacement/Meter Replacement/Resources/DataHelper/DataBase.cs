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
using SQLite;
using Android.Util;
using Meter_Replacement.Resources.Model;
using System.IO;

namespace Meter_Replacement.Resources.DataHelper
{
    public class DataBase
    {
        
        string folder = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
        public bool createDataBase()
        {
            var dbName = "Watermeters.db";
            try
            {
                
                using (var connection = new SQLiteConnection(System.IO.Path.Combine(folder, dbName)))
                {
                    connection.CreateTable<Watermeter>();
                    return true;
                }
            }
            catch (SQLiteException ex)
            {
                Log.Info("SQLiteEx", ex.Message);
                return false;
            }
        }

        public bool insertIntoTableWatermeter(Watermeter watermeter)
        {
            try
            {
                using (var connection = new SQLiteConnection(System.IO.Path.Combine(folder, "Watermeters.db")))
                {
                    connection.Insert(watermeter);
                    return true;
                }
            }
            catch (SQLiteException ex)
            {
                Log.Info("SQLiteEx", ex.Message);
                return false;
            }
        }

        public List<Watermeter> selectTableWatermeter()
        {
            try
            {
                using (var connection = new SQLiteConnection(System.IO.Path.Combine(folder, "Watermeters.db")))
                {
                    return connection.Table<Watermeter>().ToList();

                }
            }
            catch (SQLiteException ex)
            {
                Log.Info("SQLiteEx", ex.Message);
                return null;
            }
        }

        public static String doesDatabaseExist()
        {
            if (File.Exists("Watermeters.db"))
            {
                return "Yes";
            }
            else
            {
                return "No";
            }
        }

        public bool updateTableWatermeter(Watermeter watermeter)
        {
            try
            {
                using (var connection = new SQLiteConnection(System.IO.Path.Combine(folder, "Watermeter.db")))
                {
                    connection.Query<Watermeter>("UPDATE Watermeter set address=?,consumption=?,replacementID=?,comments=? Where serialNumber=?", watermeter.address, watermeter.consumption, watermeter.replacementID, watermeter.comments, watermeter.serialNumber);
                    return true;
                }
            }
            catch (SQLiteException ex)
            {
                Log.Info("SQLiteEx", ex.Message);
                return false;
            }
        }

        public bool deleteTableWatermeter(Watermeter watermeter)
        {
            try
            {
                using (var connection = new SQLiteConnection(System.IO.Path.Combine(folder, "Watermeter.db")))
                {
                    connection.Delete(watermeter);
                    return true;
                }
            }
            catch (SQLiteException ex)
            {
                Log.Info("SQLiteEx", ex.Message);
                return false;
            }
        }

        public bool selectQueryTableWatermeter(int Id)
        {
            try
            {
                using (var connection = new SQLiteConnection(System.IO.Path.Combine(folder, "Watermeter.db")))
                {
                    connection.Query<Watermeter>("SELECT * FROM Watermeter Where serialNumber=?", Id);
                    return true;
                }
            }
            catch (SQLiteException ex)
            {
                Log.Info("SQLiteEx", ex.Message);
                return false;
            }
        }

    }
}