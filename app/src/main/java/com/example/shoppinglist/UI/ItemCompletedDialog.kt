package com.example.shoppinglist.UI

import android.content.Context
import android.os.Bundle
import com.example.shoppinglist.ItemData
import kotlinx.android.synthetic.main.new_item_dialog.*

class ItemCompletedDialog (context: Context, var dialogListener2: DialogListener, var itemData1: ItemData,var completed: Boolean): EditItemDialog(context,dialogListener2,itemData1){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(completed){
            tvTitle.setText("Did you complete?")
            cbStatus.isChecked=true

        }
        else{
            tvTitle.setText("You need it again?")
            cbStatus.isChecked=false
        }
        cbStatus.isEnabled=false
        btnCancel.setText("No")
        btnSave.setText("Yes")

        btnSave.setOnClickListener{
            if(!checkForEmptyFields())
                if(checkAllFieldsValid()){
                    itemData1.name = etName.text.toString()
                    itemData1.description = etDescription.text.toString()
                    itemData1.priceInHUF = etPrice.text.toString().toInt()
                    itemData1.isStatus = cbStatus.isChecked
                    itemData1.quantity = etQuantity.text.toString().toInt()
                    itemData1.category = getItemCategory()


                    dialogListener2.onSaveButtonClicked(itemData1)
                    dismiss()
                }

        }

        btnCancel.setOnClickListener{
            dialogListener2.onSaveButtonClicked(itemData1)
            dismiss()
        }
    }
}