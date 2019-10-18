package com.example.labicarus.kotless

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_arcore.*

class ArcoreActivity: AppCompatActivity(), View.OnClickListener {


    //region //----- LATEINIT VARIABLES -----\\
    lateinit var arrayView: Array<View>
    lateinit var bearRenderable:ModelRenderable
    lateinit var catRenderable:ModelRenderable
    lateinit var cowRenderable:ModelRenderable
    lateinit var dogRenderable:ModelRenderable
    lateinit var elephantRenderable:ModelRenderable
    lateinit var ferretRenderable:ModelRenderable
    lateinit var hippoRenderable:ModelRenderable
    lateinit var horseRenderable:ModelRenderable
    lateinit var koalaRenderable:ModelRenderable
    lateinit var lionRenderable:ModelRenderable
    lateinit var reindeerRenderable:ModelRenderable
    lateinit var wolverineRenderable:ModelRenderable
    lateinit var arFragment: ArFragment
    //endregion

    internal var selected = 1

    override fun onClick(v: View?) {
        if(v!!.id == R.id.bear){
            selected = 1
            setBackground(v.id)
        }else
        if(v.id == R.id.cat){
            selected = 2
            setBackground(v.id)
        }else
        if(v.id == R.id.cow){
            selected = 3
            setBackground(v.id)
        }else
        if(v.id == R.id.dog){
            selected = 4
            setBackground(v.id)
        }else
        if(v.id == R.id.elephant){
            selected = 5
            setBackground(v.id)
        }else
        if(v.id == R.id.ferret){
            selected = 6
            setBackground(v.id)
        }else
        if(v.id == R.id.hippopotamus){
            selected = 7
            setBackground(v.id)
        }else
        if(v.id == R.id.horse){
            selected = 8
            setBackground(v.id)
        }else
        if(v.id == R.id.koala_bear) {
            selected = 9
            setBackground(v.id)
        }
        if(v.id == R.id.lion){
            selected = 10
            setBackground(v.id)
        }
        if(v.id == R.id.reindeer){
            selected = 11
            setBackground(v.id)
        }
        if(v.id == R.id.wolverine){
            selected = 12
            setBackground(v.id)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arcore)

        setupArray()
        setupClicklistener()
        setupModel()

        arFragment  = supportFragmentManager.findFragmentById(R.id.scene_form_fragment) as ArFragment

        arFragment.setOnTapArPlaneListener{ hitResult, plane, motionEvent ->
            val archor = hitResult.createAnchor()
            val anchorNode = AnchorNode(archor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            createModel(anchorNode, selected)
        }
    }

    private fun createModel(anchorNode: AnchorNode, selected: Int) {
        if(selected == 1){
            val bear = TransformableNode(arFragment.transformationSystem)
                bear.setParent(anchorNode)
                bear.renderable = bearRenderable
                bear.select()
        }
    }

    private fun setBackground(id: Any) {
        for(x in arrayView.indices){
            if(arrayView[x].id == id)
                arrayView[x].setBackgroundColor(Color.parseColor("#80333639"))
            else
                arrayView[x].setBackgroundColor(Color.TRANSPARENT)
        }
    }


    private fun setupModel() {

        ModelRenderable.builder()
            .setSource(this, R.raw.bear)
            .build()
            .thenAccept{ modelRenderable -> bearRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o urso", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.cat)
            .build()
            .thenAccept{ modelRenderable -> catRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o gato", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.cow)
            .build()
            .thenAccept{ modelRenderable -> cowRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o vaca", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.dog)
            .build()
            .thenAccept{ modelRenderable -> dogRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o cachorro", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.elephant)
            .build()
            .thenAccept{ modelRenderable -> elephantRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o elefante", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.hippopotamus)
            .build()
            .thenAccept{ modelRenderable -> hippoRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o hipopotamo", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.ferret)
            .build()
            .thenAccept{ modelRenderable -> ferretRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o furret", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.horse)
            .build()
            .thenAccept{ modelRenderable -> horseRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o cachorro", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.koala_bear)
            .build()
            .thenAccept{ modelRenderable -> koalaRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o koala", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.lion)
            .build()
            .thenAccept{ modelRenderable -> lionRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o leão", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.reindeer)
            .build()
            .thenAccept{ modelRenderable -> reindeerRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o Rena", Toast.LENGTH_SHORT).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, R.raw.wolverine)
            .build()
            .thenAccept{ modelRenderable -> wolverineRenderable = modelRenderable }
            .exceptionally { throwable ->
                Toast.makeText(this,"Impossivel de carregar o wolverine", Toast.LENGTH_SHORT).show()
                null
            }

    }

    private fun setupClicklistener() {
        for (i in arrayView.indices){
            arrayView[i].setOnClickListener(this)
        }
    }

    private fun setupArray() {
        arrayView = arrayOf(
            bear,
            cat,
            cow,
            dog,
            elephant,
            ferret,
            hippopotamus,
            horse,
            koala_bear,
            lion,
            reindeer,
            wolverine
        )
    }
}