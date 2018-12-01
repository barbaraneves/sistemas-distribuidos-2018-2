# -*- coding: utf-8 -*-

import socket, select
import sys
# import thread
import getpass
from time import sleep


host = "127.0.0.1"
porta = 4444

logado = False
cadastrado = False
receber = False
    
#Criando o Socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Endereço Ip e porta para a conexão
server_address = (host, porta)

# Conectando ao servidor
sock.connect(server_address)

entrada_saida = [sock]

# print ("Conectado")

while True:
    if not logado:
        opcoes = (
                "1 - Logar. ",
                "2 - Cadastrar-se. ",
                "3 - Sair. \n"
                )
        for opcao in opcoes:
                print(opcao)

        opt = raw_input()
        if opt == "1":
            print("Login! \n")
            login = raw_input("Login: ", )
            senha = getpass.getpass("Senha: ")
            # mensagem de logar
            mensagem = 'login-' + str(login) + '-' + str(senha)
            # Envia e recebe mensagem para o servidor
            sock.send(mensagem+'\n')
            recebido = False
            resposta = ''
            while not recebido:
                try:
                    recebidas, _, _ = select.select(entrada_saida, [], [],10)
                    if recebidas:
                        resposta = sock.recv(1024)
                        if len(resposta) != 0 and resposta != '\n':
                                recebido = True
                except:
                    print("Nenhuma resposta recebida!!")

            if resposta == "LoginRealizado":
                logado = True
            else:
                print(len(resposta))
                print('resposta:', resposta)

        elif opt == "2":
            print("\nCadastro")
            nome = raw_input("Nome do Cliente: ")
            login = raw_input("Login: ")
            senha = getpass.getpass("Senha: ")
            cpf = raw_input("Cpf: ")
            endereco = raw_input("Endereço: ")
            # dataNasc = raw_input("Data de Nascimento: ")
            mensagem = 'cadastro-' + str(nome) + '-' + str(login) + '-' + str(senha) + '-' + str(cpf) + '-' + str(endereco)
            # Envia para o servidor a requisição de cadastro e a pessoa
            sock.send(mensagem+'\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [],10)
            if recebidas:
                resposta = sock.recv(1024)
                if len(resposta) != 0:
                    print('\n'+resposta+'\n')
            
        elif opt == "3":
            sock.close()
            print ("Bye.")
            break
    
    else:
        menu = (
                "1 - Emprestimo de Livro. ",
                "2 - Ver Livros. ",
                "3 - Reserva de Livro",
                "4 - Devolução de Livro",
                "5 - Buscar Livro",
                "6 - Sair. \n"
                )                    
        for m in menu:
            print (m)

        opt = raw_input()
        if opt == "1":
            print("\nDados do Livro!")
            isbn = raw_input("Código do Livro(ISBN): ", )
            titulo_livro = raw_input("Titulo do Livro: ", )
            mensagem = 'emprestarLivro-' + str(titulo_livro) + '-' + str(isbn) + '-' + str(nome)
            sock.send(mensagem+'\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [], 10)
            if recebidas:
                resposta = sock.recv(1024)
                if len(resposta) != 0 and resposta != '\n':
                    print('\n' + resposta + '\n')
        elif opt == "2":
            mensagem = 'listarLivros'
            sock.send(mensagem+'\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [],10)
            if recebidas:
                resposta = sock.recv(1024)
                if len(resposta) != 0 and resposta != '\n':
                    print(resposta.replace(']','\n'))
        elif opt == "3":
            print("\nDados do Livro!")
            isbn = raw_input("Código do Livro(ISBN): ", )
            titulo_livro = raw_input("Titulo do Livro: ", )
            mensagem = 'reservarLivro-' + str(titulo_livro) + '-' + str(isbn) + '-' + str(nome)
            sock.send(mensagem + '\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [], 10)
            if recebidas:
                resposta = sock.recv(1024)
                if len(resposta) != 0 and resposta != '\n':
                    print('\n' + resposta + '\n')
        elif opt == "4":
            print("\nDados do Livro!")
            isbn = raw_input("Código do Livro(ISBN): ", )
            titulo_livro = raw_input("Titulo do Livro: ", )
            mensagem = 'devolverLivro-' + str(titulo_livro) + '-' + str(isbn) + '-' + str(nome)
            sock.send(mensagem + '\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [], 10)
            if recebidas:
                resposta = sock.recv(1024)
                if len(resposta) != 0 and resposta != '\n':
                    print('\n' + resposta + '\n')
        elif opt == "5":
            pass
        elif opt == "6":
            logado = False
        else:
            print("OPÇÃO NÃO SUPORTADA!!")
 
sock.close()
