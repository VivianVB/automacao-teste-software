package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LancamentoPage {

    private WebDriver driver;

    public LancamentoPage(final WebDriver driver){
        this.driver = driver;
    }

    public void cria(final String descricaoLancamento, final BigDecimal valorLancamento,
                     LocalDateTime dataHora, TipoLancamento tipo, final String categoriaLancamento){

        // Vivian - modificações na seleção de tipo - parte 1
        WebElement tipoFinal;

        if(tipo == TipoLancamento.SAIDA){
            tipoFinal = driver.findElement(By.id("tipoLancamento2")); // informa lançamento: SAÍDA
        }else{
            tipoFinal = driver.findElement(By.id("tipoLancamento1")); // informa lançamento: ENTRADA
        }

        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.sendKeys(descricaoLancamento);

        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        WebElement dataLancamento = driver.findElement(By.name("dataLancamento"));
        dataLancamento.sendKeys(dataHora.format(formatoDataLancamento));

        WebElement valor = driver.findElement(By.id("valor"));
        //Vivian - modificações na seleção de tipo - parte 2
        tipoFinal.click();
        valor.sendKeys(String.valueOf(valorLancamento));

        // Vivian - seleção de categoria
        WebElement categoria = driver.findElement(By.id("categoria"));
        Select selectCategoria = new Select(categoria);
        selectCategoria.selectByValue(categoriaLancamento);

        driver.findElement(By.id("btnSalvar")).click();
    }

    public void buscaLancamento(final String descricaoLancamento){

        WebElement descricao = driver.findElement(By.id("itemBusca"));
        descricao.click();
        descricao.sendKeys(descricaoLancamento);

        driver.findElement(By.id("bth-search")).click();

        //*************thread para testar execução - remover*************
        try{Thread.sleep(5000);}
        catch(InterruptedException ie){}
        //***************************************************************
    }

    public void edita(final String novaDescricao, final BigDecimal valorLancamento,
                      LocalDateTime dataHora, TipoLancamento tipo, final String categoriaLancamento){

        WebElement edicao = driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[1]//a[contains(@id,'editar')]"));
        edicao.click();

        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.clear();
        descricao.sendKeys(novaDescricao);

        driver.findElement(By.id("btnSalvar")).click();

        //*************thread para testar execução - remover*************
        try{Thread.sleep(5000);}
        catch(InterruptedException ie){}
        //***************************************************************
    }

    public void remove(){

        WebElement remocao = driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tbody/tr[1]//a[contains(@id,'remover')]"));
        remocao.click();

        //*************thread para testar execução - remover*************
        try{Thread.sleep(5000);}
        catch(InterruptedException ie){}
        //***************************************************************
    }

    public String validaTotalEntrada(){

        String entrada = driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tfoot/tr[2]/th/span")).getText();
        return entrada;
    }

    public String validaTotalSaida(){

        String saida = driver.findElement(By.xpath("//*[@id=\"tabelaLancamentos\"]/tfoot/tr[1]/th/span")).getText();
        return saida;
    }

    // Peguei um exemplo da web para tentar fazer: https://stackoverflow.com/questions/6198947/how-to-get-text-from-each-cell-of-an-html-table
    public BigDecimal percorreLancamentosSaidas()
    {
        // Vivian - criação de variáveis
        String caminhoTipo;
        String resultadoTipo;
        String caminho;
        BigDecimal soma = BigDecimal.ZERO;
        String valor;

        WebElement table_element = driver.findElement(By.id("tabelaLancamentos"));
        List<WebElement> tr_collection = table_element.findElements(By.xpath("id('tabelaLancamentos')/tbody/tr"));

        int row_num, col_num;
        row_num=1;
        for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
            caminhoTipo = "//*[@id=\"tabelaLancamentos\"]/tbody/tr[" + row_num + "]/td[5]";
            resultadoTipo = driver.findElement(By.xpath(caminhoTipo)).getText();
            caminho = "//*[@id=\"tabelaLancamentos\"]/tbody/tr[" + row_num + "]/td[4]";
            valor = driver.findElement(By.xpath(caminho)).getText();
            valor = valor.replaceAll(",", ".");

            if(resultadoTipo.equals("Saída"))
            {    soma = soma.add(new BigDecimal(valor));}
            row_num++;
        }
        return soma;
    }

    public BigDecimal percorreLancamentosEntradas()
    {
        // Vivian - criação de variáveis
        String caminhoTipo;
        String resultadoTipo;
        String caminho;
        BigDecimal soma = BigDecimal.ZERO;
        String valor;

        WebElement table_element = driver.findElement(By.id("tabelaLancamentos"));
        List<WebElement> tr_collection = table_element.findElements(By.xpath("id('tabelaLancamentos')/tbody/tr"));

        int row_num, col_num;
        row_num=1;
        for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
            caminhoTipo = "//*[@id=\"tabelaLancamentos\"]/tbody/tr[" + row_num + "]/td[5]";
            resultadoTipo = driver.findElement(By.xpath(caminhoTipo)).getText();
            caminho = "//*[@id=\"tabelaLancamentos\"]/tbody/tr[" + row_num + "]/td[4]";
            valor = driver.findElement(By.xpath(caminho)).getText();
            valor = valor.replaceAll(",", ".");

            if(resultadoTipo.equals("Entrada"))
            {    soma = soma.add(new BigDecimal(valor));}
            row_num++;
        }
        return soma;
    }

    public void acessaGrafico(){

        WebElement grafico = driver.findElement(By.xpath("//a[contains(@title,'Gráfico')]"));
        grafico.click();

        //*************thread para testar execução - remover*************
        try{Thread.sleep(5000);}
        catch(InterruptedException ie){}
        //***************************************************************
    }
}


