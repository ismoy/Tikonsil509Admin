package com.tikonsil.tikonsil509admin.data.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateUserProvider
import com.tikonsil.tikonsil509admin.databinding.ItemRegisteredUsersContentBinding
import com.tikonsil.tikonsil509admin.domain.model.RegisteredUser
import com.tikonsil.tikonsil509admin.utils.Constant

/** * Created by ISMOY BELIZAIRE on 13/05/2022. */
class RegistreredUserAdapter(val context: Context) :
    RecyclerView.Adapter<RegistreredUserAdapter.MyViewHolder>() {
    private var userregistrered = emptyList<RegisteredUser>()

    @SuppressLint("NotifyDataSetChanged")
    fun setsaleListData(data: List<RegisteredUser>) {
        userregistrered = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemRegisteredUsersContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val usersdata = userregistrered[position]
        holder.bidView(usersdata, context)
    }

    override fun getItemCount(): Int {
        return if (userregistrered.isNotEmpty())
            userregistrered.size else {
            0
        }
    }

    class MyViewHolder(private val binding: ItemRegisteredUsersContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bidView(usersdata: RegisteredUser, context: Context) {
            val dialog by lazy { Dialog(context) }
            val updateuserprovider by lazy { UpdateUserProvider() }
            val mConstant by lazy { Constant() }
            binding.apply {
                firstnameregisteruser.text = usersdata.firstname
                lastnameregisteruser.text = usersdata.lastname
                emailregisteruser.text = usersdata.email
                passwordregisteruser.text = usersdata.password
                when (usersdata.status) {
                    1 -> {
                        estadoregisteruser.text = context.getString(R.string.activate)
                        estadoregisteruser.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorverde
                            )
                        )
                        switchregisteruser.isChecked = true
                    }
                    else -> {
                        estadoregisteruser.text = context.getString(R.string.desactivate)
                        estadoregisteruser.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorerror
                            )
                        )
                        switchregisteruser.isChecked = false
                    }
                }
                telefonoregisteruser.text = usersdata.phone
                when (usersdata.role) {
                    1 -> {
                        cargoregisteruser.text = context.getString(R.string.agent)
                    }
                    else -> {
                        cargoregisteruser.text = context.getString(R.string.master)

                    }
                }
                when (usersdata.countrycode) {
                    "HT" -> {
                        topupregisteruser.text = "HTG ${usersdata.soldtopup} "
                        moncashregisteruser.text = "HTG ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="HTG ${usersdata.soldnatcash}"
                        lapoularegisteruser.text ="HTG ${usersdata.soldlapoula}"
                    }
                    "CL" -> {
                        topupregisteruser.text = "CLP ${usersdata.soldtopup} "
                        moncashregisteruser.text = "CLP ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="CLP ${usersdata.soldnatcash}"
                        lapoularegisteruser.text ="CLP ${usersdata.soldlapoula}"
                    }
                    "DO" -> {
                        topupregisteruser.text = "DOP ${usersdata.soldtopup} "
                        moncashregisteruser.text = "DOP ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="DOP ${usersdata.soldnatcash}"
                        lapoularegisteruser.text ="DOP ${usersdata.soldlapoula}"
                    }
                    "PA" -> {
                        topupregisteruser.text = "PAB ${usersdata.soldtopup} "
                        moncashregisteruser.text = "PAB ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="PAB ${usersdata.soldnatcash}"
                        lapoularegisteruser.text ="PAB ${usersdata.soldlapoula}"
                    }
                    "MX" -> {
                        topupregisteruser.text = "MXN ${usersdata.soldtopup} "
                        moncashregisteruser.text = "MXN ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="MXN ${usersdata.soldnatcash}"
                        lapoularegisteruser.text ="MXN ${usersdata.soldlapoula}"
                    }
                    "BR" -> {
                        topupregisteruser.text = "BRL ${usersdata.soldtopup} "
                        moncashregisteruser.text = "BRL ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="BRL ${usersdata.soldnatcash}"
                        lapoularegisteruser.text ="BRL ${usersdata.soldlapoula}"
                    }
                    "CU" -> {
                        topupregisteruser.text = "CUB ${usersdata.soldtopup} "
                        moncashregisteruser.text = "CUB ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="CUB ${usersdata.soldnatcash}"
                        lapoularegisteruser.text ="CUB ${usersdata.soldlapoula}"
                    }
                    else -> {
                        topupregisteruser.text = "$ ${usersdata.soldtopup} "
                        moncashregisteruser.text = "$ ${usersdata.soldmoncash} "
                        natcashregisteruser.text ="$ ${usersdata.soldnatcash}"
                        lapoularegisteruser.text =" ${usersdata.soldlapoula}"
                    }
                }
                imageeditregistreduser.setOnClickListener {
                    dialog.setContentView(R.layout.custom_edit_dialog)
                    val window: Window? = dialog.window
                    window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    dialog.setCancelable(false)
                    if (dialog.window != null) {
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
                    }
                    dialog.show()

                    dialog.apply {
                        val button_cancel = findViewById<Button>(R.id.btn_edit_dialog_cancel)
                        val btn_edit_dialog = findViewById<Button>(R.id.btn_edit_dialog)
                        val firstnamedialog = findViewById<TextInputEditText>(R.id.firstnamedialog)
                        val lastnamedialog = findViewById<TextInputEditText>(R.id.lastnamedialog)
                        val passworddialog = findViewById<TextInputEditText>(R.id.passworddialog)
                        val phonedialog = findViewById<TextInputEditText>(R.id.phonedialog)
                        val roledialog = findViewById<TextInputEditText>(R.id.roledialog)
                        val topupdialog = findViewById<TextInputEditText>(R.id.topupdialog)
                        val moncashdialog = findViewById<TextInputEditText>(R.id.moncashdialog)
                        val layoutfirstname = findViewById<TextInputLayout>(R.id.layoutfirstname)
                        val layoutlastname = findViewById<TextInputLayout>(R.id.layoutlastname)
                        val layoutpasswordname =
                            findViewById<TextInputLayout>(R.id.layoutpasswordname)
                        val layoutrolename = findViewById<TextInputLayout>(R.id.layoutrolename)
                        val layouttopupname = findViewById<TextInputLayout>(R.id.layouttopupname)
                        val layoutmoncashname = findViewById<TextInputLayout>(R.id.layoutmoncashname)
                        val layoutphonename = findViewById<TextInputLayout>(R.id.layoutphonename)
                        val layoutnatcashname = findViewById<TextInputLayout>(R.id.layoutnatcashname)
                        val natcashdialog =findViewById<TextInputEditText>(R.id.natcashdialog)
                        val layoutlapoulaname = findViewById<TextInputLayout>(R.id.layoutlapoulaname)
                        val lapouladialog =findViewById<TextInputEditText>(R.id.lapouladialog)

                        //set data in edit text
                        setDataInEditText(firstnamedialog, lastnamedialog, passworddialog, phonedialog,
                            roledialog, topupdialog, moncashdialog,natcashdialog,lapouladialog,usersdata
                        )
                        //validate Real Time
                        validateRealtime(firstnamedialog, lastnamedialog, passworddialog, phonedialog,
                            roledialog, topupdialog, moncashdialog, layoutfirstname, layoutlastname,
                            layoutpasswordname, layoutrolename, layouttopupname, layoutmoncashname,
                            context, mConstant, layoutphonename,layoutnatcashname,layoutlapoulaname,natcashdialog,lapouladialog
                        )
                        button_cancel.setOnClickListener {
                            dialog.dismiss()
                        }
                        btn_edit_dialog.setOnClickListener {
                            ValidateOnClickButton(firstnamedialog, lastnamedialog, passworddialog, phonedialog,
                                roledialog, topupdialog, moncashdialog, layoutfirstname, layoutlastname,
                                layoutpasswordname, layoutrolename, layouttopupname, layoutmoncashname,
                                context, mConstant, layoutphonename,natcashdialog,lapouladialog,layoutnatcashname,layoutlapoulaname,usersdata, dialog
                            )
                        }
                        deleteuser.setOnClickListener {
                            deleteUser(context,usersdata)
                        }
                    }

                }
                switchregisteruser.setOnClickListener {
                    if (switchregisteruser.isChecked) {
                        usersdata.status = 1
                        updateuserprovider.update(usersdata)
                        switchregisteruser.isChecked = true
                        estadoregisteruser.text = context.getString(R.string.activate)
                        estadoregisteruser.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorverde
                            )
                        )
                    } else {
                        usersdata.status = 0
                        updateuserprovider.update(usersdata)
                        switchregisteruser.isChecked = false
                        estadoregisteruser.text = context.getString(R.string.desactivate)
                        estadoregisteruser.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorerror
                            )
                        )
                    }
                }
            }
        }

        private fun deleteUser(context: Context, usersdata: RegisteredUser) {
            val builder = AlertDialog.Builder(context)
            val authprovider by lazy { AuthProvider() }
            builder.setTitle("Estas seguro que quieres Eliminar esa cuenta?")
            builder.setMessage("Al eliminar la cuanta el usuario ya no tendra acceso a su cuenta y no puede recuperarla")
            builder.setPositiveButton("Delete") { dialog, which ->
                authprovider.deleteAccount()?.addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "No se puede eliminar la cuenta solo puedes desactivar el usuario ", Toast.LENGTH_LONG).show()
                    }
                }

            }
            builder.setNegativeButton("No") { dialog1, which ->
               dialog1.dismiss()
            }
            builder.show()


        }

        private fun ValidateOnClickButton(
            firstnamedialog: TextInputEditText?,
            lastnamedialog: TextInputEditText?,
            passworddialog: TextInputEditText?,
            phonedialog: TextInputEditText?,
            roledialog: TextInputEditText?,
            topupdialog: TextInputEditText?,
            moncashdialog: TextInputEditText?,
            layoutfirstname: TextInputLayout?,
            layoutlastname: TextInputLayout?,
            layoutpasswordname: TextInputLayout?,
            layoutrolename: TextInputLayout?,
            layouttopupname: TextInputLayout?,
            layoutmoncashname: TextInputLayout?,
            context: Context,
            mConstant: Constant,
            layoutphonename: TextInputLayout?,
            natcashdialog: TextInputEditText,
            lapouladialog: TextInputEditText,
            layoutnatcashname: TextInputLayout,
            layoutlapoulaname: TextInputLayout,
            usersdata: RegisteredUser,
            dialog: Dialog
        ) {
            when {
                firstnamedialog?.text.toString().isEmpty() -> {
                    layoutfirstname?.helperText = context.getString(R.string.erroremptyfield)
                }
                !mConstant.validateonlyleter(firstnamedialog?.text.toString()) -> {
                    layoutfirstname?.helperText =
                        context.getString(R.string.error_firsname_onlyletter)
                }
                else -> {
                layoutfirstname?.helperText=""
                }
            }
            when {
                lastnamedialog?.text.toString().isEmpty() -> {
                    layoutlastname?.helperText = context.getString(R.string.erroremptyfield)
                }
                !mConstant.validateonlyleter(lastnamedialog?.text.toString()) -> {
                    layoutlastname?.helperText =
                        context.getString(R.string.error_lastname_onlyletter)
                }
                else -> {
                layoutlastname?.helperText=""
                }
            }
            when {
                passworddialog?.text.toString().isEmpty() -> {
                    layoutpasswordname?.helperText = context.getString(R.string.erroremptyfield)
                }
                !mConstant.validatelongitudepassword(passworddialog?.text.toString()) -> {
                    layoutpasswordname?.helperText =
                        context.getString(R.string.error_longitudepassword)
                }
                else -> {
                    layoutpasswordname?.helperText=""

                }
            }

            when {
                phonedialog?.text.toString().isEmpty() -> {
                    layoutphonename?.helperText = context.getString(R.string.erroremptyfield)
                }
                !mConstant.validatelenghnumberphone(phonedialog?.text.toString()) -> {
                    layoutphonename?.helperText =
                        context.getString(R.string.error_longitud_number)
                }
                else -> {
                    layoutphonename?.helperText=""
                }
            }
            when {
                roledialog?.text.toString().isEmpty() -> {
                    layoutrolename?.helperText = context.getString(R.string.erroremptyfield)
                }
                roledialog?.text.toString().toInt() == 1 || roledialog?.text.toString()
                    .toInt() == 2 -> {
                    layoutrolename?.helperText = ""
                }
                else -> {
                    layoutrolename?.helperText=""
                }
            }

            when {
                topupdialog?.text.toString().isEmpty() -> {
                    layouttopupname?.helperText = context.getString(R.string.erroremptyfield)
                }
                else -> {
                    layouttopupname?.helperText=""

                }
            }
            when {
                moncashdialog?.text.toString().isEmpty() -> {
                    layoutmoncashname?.helperText = context.getString(R.string.erroremptyfield)
                }
                else -> {
                    layoutmoncashname?.helperText=""
                }
            }
            when {
                natcashdialog.text.toString().isEmpty() -> {
                    layoutnatcashname.helperText = context.getString(R.string.erroremptyfield)
                }
                else -> {
                    layoutnatcashname.helperText =""
                }
            }
            when {
                lapouladialog.text.toString().isEmpty() -> {
                    layoutlapoulaname.helperText = context.getString(R.string.erroremptyfield)
                }
                else -> {
                    layoutlapoulaname.helperText =""
                    val map: MutableMap<String?, Any?> = HashMap()
                    map.apply {
                        put("firstname", firstnamedialog?.text.toString())
                        put("lastname", lastnamedialog?.text.toString())
                        put("phone", phonedialog?.text.toString())
                        if (moncashdialog?.text.toString().toDouble()==usersdata.soldmoncash){
                            put("soldmoncash",usersdata.soldmoncash)
                        }else{
                            put("soldmoncash", moncashdialog?.text.toString().toDouble() + usersdata.soldmoncash)
                        }
                        if (topupdialog?.text.toString().toDouble()==usersdata.soldtopup){
                            put("soldtopup",usersdata.soldtopup)
                        }else{
                            put("soldtopup", topupdialog?.text.toString().toDouble() + usersdata.soldtopup)
                        }
                        if (natcashdialog.text.toString().toDouble()==usersdata.soldnatcash){
                            put("soldnatcash",usersdata.soldnatcash)
                        }else{
                            put("soldnatcash", natcashdialog.text.toString().toDouble() + usersdata.soldnatcash)
                        }
                        if (lapouladialog.text.toString().toDouble()==usersdata.soldlapoula){
                            put("soldlapoula",usersdata.soldlapoula)
                        }else{
                            put("soldlapoula", lapouladialog.text.toString().toDouble() + usersdata.soldlapoula)
                        }
                        put("role", roledialog?.text.toString().toInt())
                        put("password", passworddialog?.text.toString())
                    }
                    val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Clients")
                    mDatabase.child(usersdata.id.toString()).updateChildren(map)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(dialog.context, "Actualizado correctamente", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }else{
                                Toast.makeText(dialog.context, "error al cambiar los datos", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                        }
                }
            }

        }


        private fun validateRealtime(
            firstnamedialog: TextInputEditText?,
            lastnamedialog: TextInputEditText?,
            passworddialog: TextInputEditText?,
            phonedialog: TextInputEditText?,
            roledialog: TextInputEditText?,
            topupdialog: TextInputEditText?,
            moncashdialog: TextInputEditText?,
            layoutfirstname: TextInputLayout,
            layoutlastname: TextInputLayout,
            layoutpasswordname: TextInputLayout,
            layoutrolename: TextInputLayout,
            layouttopupname: TextInputLayout,
            layoutmoncashname: TextInputLayout,
            context: Context,
            mConstant: Constant,
            layoutphonename: TextInputLayout,
            layoutnatcashname: TextInputLayout,
            layoutlapoulaname: TextInputLayout,
            natcashdialog: TextInputEditText,
            lapouladialog: TextInputEditText,
        ) {
            firstnamedialog?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        firstnamedialog.text.toString().isEmpty() -> {
                            layoutfirstname.helperText = context.getString(R.string.erroremptyfield)

                        }
                        !mConstant.validateonlyleter(firstnamedialog.text.toString()) -> {
                            layoutfirstname.helperText =
                                context.getString(R.string.error_firsname_onlyletter)
                        }
                        else -> {
                            layoutfirstname.helperText = ""
                        }

                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            lastnamedialog?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        lastnamedialog.text.toString().isEmpty() -> {
                            layoutlastname.helperText = context.getString(R.string.erroremptyfield)

                        }
                        !mConstant.validateonlyleter(lastnamedialog.text.toString()) -> {
                            layoutlastname.helperText =
                                context.getString(R.string.error_lastname_onlyletter)
                        }
                        else -> {
                            layoutlastname.helperText = ""
                        }

                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            passworddialog?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        passworddialog.text.toString().isEmpty() -> {
                            layoutpasswordname.helperText =
                                context.getString(R.string.erroremptyfield)

                        }
                        !mConstant.validatelongitudepassword(passworddialog.text.toString()) -> {
                            layoutpasswordname.helperText =
                                context.getString(R.string.error_longitudepassword)
                        }
                        else -> {
                            layoutpasswordname.helperText = ""
                        }

                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            phonedialog?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        phonedialog.text.toString().isEmpty() -> {
                            layoutphonename.helperText = context.getString(R.string.erroremptyfield)

                        }
                        !mConstant.validatelenghnumberphone(phonedialog.text.toString()) -> {
                            layoutphonename.helperText =
                                context.getString(R.string.error_longitud_number)
                        }
                        else -> {
                            layoutphonename.helperText = ""
                        }

                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            roledialog?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        roledialog.text.toString().isEmpty() -> {
                            layoutrolename.helperText = context.getString(R.string.erroremptyfield)
                        }
                        roledialog.text.toString().toInt() == 1 || roledialog.text.toString()
                            .toInt() == 2 -> {
                            layoutrolename.helperText = ""
                        }
                        else -> {
                            layoutrolename.helperText =
                                context.getString(R.string.error_onlyoneandtwo_eole)
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            topupdialog?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        topupdialog.text.toString().isEmpty() -> {
                            layouttopupname.helperText = context.getString(R.string.erroremptyfield)
                        }
                        else -> {
                            layouttopupname.helperText = ""

                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            moncashdialog?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        moncashdialog.text.toString().isEmpty() -> {
                            layoutmoncashname.helperText =
                                context.getString(R.string.erroremptyfield)
                        }
                        else -> {
                            layoutmoncashname.helperText = ""
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            natcashdialog.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        natcashdialog.text.toString().isEmpty() -> {
                            layoutnatcashname.helperText =
                                context.getString(R.string.erroremptyfield)
                        }
                        else -> {
                            layoutnatcashname.helperText = ""
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
            lapouladialog.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when {
                        lapouladialog.text.toString().isEmpty() -> {
                            layoutlapoulaname.helperText =
                                context.getString(R.string.erroremptyfield)
                        }
                        else -> {
                            layoutlapoulaname.helperText = ""
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }

        private fun setDataInEditText(
            firstnamedialog: TextInputEditText?,
            lastnamedialog: TextInputEditText?,
            passworddialog: TextInputEditText?,
            phonedialog: TextInputEditText?,
            roledialog: TextInputEditText?,
            topupdialog: TextInputEditText?,
            moncashdialog: TextInputEditText?,
            natcashdialog: TextInputEditText,
            lapouladialog: TextInputEditText,
            usersdata: RegisteredUser,
        ) {
            firstnamedialog?.setText(usersdata.firstname)
            lastnamedialog?.setText(usersdata.lastname)
            passworddialog?.setText(usersdata.password)
            phonedialog?.setText(usersdata.phone)
            roledialog?.setText(usersdata.role.toString())
            topupdialog?.setText(usersdata.soldtopup.toString())
            moncashdialog?.setText(usersdata.soldmoncash.toString())
            natcashdialog.setText(usersdata.soldnatcash.toString())
            lapouladialog.setText(usersdata.soldlapoula.toString())

        }

    }

}