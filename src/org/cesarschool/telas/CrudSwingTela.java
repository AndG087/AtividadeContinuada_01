package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrudSwingTela extends JFrame {
    private EntidadeMediator mediator;

    public CrudSwingTela() {
        mediator = new EntidadeMediator();
        inicializarTela();
    }

    private void inicializarTela() {
        setTitle("Sistema CRUD de Entidades");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel Principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Botões de Ações
        JButton btnIncluir = new JButton("Incluir Entidade");
        JButton btnAlterar = new JButton("Alterar Entidade");
        JButton btnExcluir = new JButton("Excluir Entidade");
        JButton btnBuscar = new JButton("Buscar Entidade");
        JButton btnSair = new JButton("Sair");

        // Ações dos Botões
        btnIncluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaIncluir();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaAlterar();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaExcluir();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaBuscar();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adicionar botões ao painel
        panel.add(btnIncluir);
        panel.add(btnAlterar);
        panel.add(btnExcluir);
        panel.add(btnBuscar);
        panel.add(btnSair);

        // Adicionar painel à tela principal
        add(panel, BorderLayout.CENTER);
    }

    private void abrirTelaIncluir() {
        JFrame frame = new JFrame("Incluir Entidade");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));

        JLabel lblCodigo = new JLabel("Código:");
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblRenda = new JLabel("Renda:");
        JTextField txtCodigo = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtRenda = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigo.getText();
                String nome = txtNome.getText();
                double renda = Double.parseDouble(txtRenda.getText());

                Entidade entidade = new Entidade(codigo, nome, renda);
                String resultado = mediator.incluir(entidade);

                JOptionPane.showMessageDialog(frame, resultado);
            }
        });

        // Adicionar componentes ao frame
        frame.add(lblCodigo);
        frame.add(txtCodigo);
        frame.add(lblNome);
        frame.add(txtNome);
        frame.add(lblRenda);
        frame.add(txtRenda);
        frame.add(new JLabel()); // Espaço vazio
        frame.add(btnSalvar);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirTelaAlterar() {
        JFrame frame = new JFrame("Alterar Entidade");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));

        JLabel lblCodigo = new JLabel("Código:");
        JLabel lblNome = new JLabel("Novo Nome:");
        JLabel lblRenda = new JLabel("Nova Renda:");
        JTextField txtCodigo = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtRenda = new JTextField();
        JButton btnAlterar = new JButton("Alterar");

        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigo.getText();
                Entidade entidade = mediator.buscar(codigo);

                if (entidade != null) {
                    String nome = txtNome.getText();
                    double renda = Double.parseDouble(txtRenda.getText());
                    entidade.setNome(nome);
                    entidade.setRenda(renda);

                    String resultado = mediator.alterar(entidade);
                    JOptionPane.showMessageDialog(frame, resultado);
                } else {
                    JOptionPane.showMessageDialog(frame, "Entidade não encontrada!");
                }
            }
        });

        // Adicionar componentes ao frame
        frame.add(lblCodigo);
        frame.add(txtCodigo);
        frame.add(lblNome);
        frame.add(txtNome);
        frame.add(lblRenda);
        frame.add(txtRenda);
        frame.add(new JLabel()); // Espaço vazio
        frame.add(btnAlterar);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirTelaExcluir() {
        JFrame frame = new JFrame("Excluir Entidade");
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(2, 2));

        JLabel lblCodigo = new JLabel("Código:");
        JTextField txtCodigo = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigo.getText();
                String resultado = mediator.excluir(codigo);
                JOptionPane.showMessageDialog(frame, resultado);
            }
        });

        // Adicionar componentes ao frame
        frame.add(lblCodigo);
        frame.add(txtCodigo);
        frame.add(new JLabel()); // Espaço vazio
        frame.add(btnExcluir);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirTelaBuscar() {
        JFrame frame = new JFrame("Buscar Entidade");
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(3, 2));

        JLabel lblCodigo = new JLabel("Código:");
        JTextField txtCodigo = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        JLabel lblResultado = new JLabel();
        
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigo.getText();
                Entidade entidade = mediator.buscar(codigo);

                if (entidade != null) {
                    lblResultado.setText("<html>Código: " + entidade.getCodigo() +
                                         "<br>Nome: " + entidade.getNome() +
                                         "<br>Renda: " + entidade.getRenda() + "</html>");
                } else {
                    lblResultado.setText("Entidade não encontrada!");
                }
            }
        });

        // Adicionar componentes ao frame
        frame.add(lblCodigo);
        frame.add(txtCodigo);
        frame.add(new JLabel()); // Espaço vazio
        frame.add(btnBuscar);
        frame.add(lblResultado);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        CrudSwingTela tela = new CrudSwingTela();
        tela.setVisible(true);
    }
}
