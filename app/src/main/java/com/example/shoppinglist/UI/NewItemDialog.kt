package com.example.shoppinglist.UI

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.core.view.get
import com.example.shoppinglist.Category
import com.example.shoppinglist.ItemData
import com.example.shoppinglist.R
import com.example.shoppinglist.R.*
import kotlinx.android.synthetic.main.new_item_dialog.*

open class NewItemDialog(context: Context, var dialogListener: DialogListener): AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layout.new_item_dialog)


        val categories = context.resources.getStringArray(array.categories)
        val adapter = ArrayAdapter(this.context!!,android.R.layout.simple_spinner_item,categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnCategory.adapter = adapter

        btnSave.setOnClickListener{
            if(!checkForEmptyFields())
                if(checkAllFieldsValid()){
                    val name = etName.text.toString()
                    val description = etDescription.text.toString()
                    val price = etPrice.text.toString().toInt()
                    val status = cbStatus.isChecked
                    val quantity = etQuantity.text.toString().toInt()
                    val category = getItemCategory()

                    var newItem = ItemData(category,name,description,price,status,quantity)
                    dialogListener.onSaveButtonClicked(newItem)
                    dismiss()
                }

        }

        btnCancel.setOnClickListener{
            cancel()
        }

    }

    fun checkAllFieldsValid(): Boolean{
        var fieldsAreValid = true
        if(etPrice.text.toString().toInt()<0){
            etPrice.error = "Please, insert a valid price*"
            fieldsAreValid=false
        }
        if(etQuantity.text.toString().toInt()<0){
            etQuantity.error = "Please, insert a valid amount*"
            fieldsAreValid=false
        }
        return fieldsAreValid
    }

    fun checkForEmptyFields(): Boolean{
        var emptyFound = false
        if(etName.text.isEmpty()){
            etName.error = "Please, add a name*"
            emptyFound=true
        }
        if(etQuantity.text.isEmpty()){
            etQuantity.error = "Please, add amount*"
            emptyFound=true
        }
        if(etPrice.text.isEmpty()){
            etPrice.error = "Please, add price*"
            emptyFound=true
        }
        if(spnCategory.selectedItem==null){
            Toast.makeText(context,"Please choose any category*",Toast.LENGTH_SHORT).show()
            emptyFound=true
        }
        return emptyFound
    }

    fun getItemCategory(): Category{
        var itemCategory: Category
        when(spnCategory.selectedItemPosition){
            1 -> itemCategory = Category.electronic
            2 -> itemCategory = Category.clothing
            3 -> itemCategory = Category.book
            else -> itemCategory = Category.food
        }
        return itemCategory
    }
}