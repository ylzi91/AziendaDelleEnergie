package team1BW.AziendaDelleEnergie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team1BW.AziendaDelleEnergie.comuni.ComuneService;
import team1BW.AziendaDelleEnergie.provincie.ProvinciaService;

@Component
public class CsvRunner implements CommandLineRunner {
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private ComuneService comuneService;

    @Override
    public void run(String... args) throws Exception {
//        provinciaService.saveAllProvincie("src/main/java/team1BW/AziendaDelleEnergie/files/province-italiane.csv");
//        comuneService.saveAllComuni("src/main/java/team1BW/AziendaDelleEnergie/files/comuni-italiani.csv");
    }
}
