package br.edu.ifsp.scl.ads.pdm.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.ads.pdm.dados.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBiding: ActivityMainBinding
    private lateinit var geradorRandomico: Random
    private lateinit var settingsActivityLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBiding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBiding.root)

        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBiding.jogarDadoBt.setOnClickListener{
            val resultado: Int = geradorRandomico.nextInt()
            "A face sorteada foi $resultado".also { activityMainBiding.resultadoTv.text = it }
            val nomeImage = "dice_${resultado}"
            activityMainBiding.resultadoIv.setImageResource(
                resources.getIdentifier(nomeImage, "mipmap", packageName)
            )
        }

        settingsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK){
                if(result.data != null){
                    val configuracao: Configuracao? = result.data?.getParcelableExtra<Configuracao>(Intent.EXTRA_USER)
                    if (configuracao != null) {
                        auxConfig = configuracao

                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settingsMi){

            return true
        }
        return false

    }
}

