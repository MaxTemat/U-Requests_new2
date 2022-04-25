package com.example.u_requests

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_urequest)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        findViewById<ImageView>(R.id.imageMenu).setOnClickListener {
          drawerLayout.openDrawer(GravityCompat.START)
        }
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.itemIconTintList = null

        findViewById<FloatingActionButton>(R.id.add_requests_fba).also {
            it.setOnClickListener {
                val intent = Intent(this, AddRequestActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        MyData.positionsSelected.removeAll(MyData.positionsSelected)
        MyData.selectCount = 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.my_menu, menu)

        menu?.get(0)?.let { MyData.menuItems.add(it) }
        menu?.get(1)?.let { MyData.menuItems.add(it) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1)
            recreate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all)
            confirmDeleteAllDialog()
        else if (item.itemId == R.id.delete_item){
            if (MyData.listIdToDelete.size > 0)
                confirmDeleteDialog()
        }
        else if (item.itemId == R.id.edit_item) {
            val intent = Intent(this, AddRequestActivity::class.java)
            startActivityForResult(intent, 1)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun confirmDeleteDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete item(s) ?")
        builder.setMessage("You really want to delete ${MyData.selectCount} item(s) ?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            MyData.listIdToDelete.forEach {
                MyDataBaseHelper(this).deleteData(it)
            }
            recreate()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->
            recreate()
        })
        builder.create().show()
    }
    private fun confirmDeleteAllDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete item(s) ?")
        builder.setMessage("You really want to delete all items ?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            MyDataBaseHelper(this).deleteAllData()
            recreate()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->
            recreate()
        })
        builder.create().show()
    }
}