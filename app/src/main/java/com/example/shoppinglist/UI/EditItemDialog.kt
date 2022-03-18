package com.example.shoppinglist.UI

import android.content.Context
import android.os.Bundle
import com.example.shoppinglist.Category
import com.example.shoppinglist.ItemData
import kotlinx.android.synthetic.main.new_item_dialog.*

open class EditItemDialog(context: Context, var dialogListener1: DialogListener, var itemData: ItemData): NewItemDialog(context, dialogListener1) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.setText("Edit your item:")
        spnCategory.setSelection(getSpnPosition())
        etName.setText(itemData.name)
        etDescription.setText(itemData.description)
        etPrice.setText(itemData.priceInHUF.toString())
        etQuantity.setText(itemData.quantity.toString())
        cbStatus.isChecked=itemData.isStatus

        btnSave.setOnClickListener{
            if(!checkForEmptyFields())
                if(checkAllFieldsValid()){
                    itemData.name = etName.text.toString()
                    itemData.description = etDescription.text.toString()
                    itemData.priceInHUF = etPrice.text.toString().toInt()
                    itemData.isStatus = cbStatus.isChecked
                    itemData.quantity = etQuantity.text.toString().toInt()
                    itemData.category = getItemCategory()


                    dialogListener1.onSaveButtonClicked(itemData)
                    dismiss()
                }

        }

        btnCancel.setOnClickListener{
            cancel()
        }
    }

    private fun getSpnPosition(): Int{
        when(itemData.category){
            Category.electronic -> return 1
            Category.clothing -> return 2
            Category.book -> return 3
            else -> return 0
        }
    }

}