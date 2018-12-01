# -*- coding: utf-8 -*-

import socket, select
import sys
# import thread
import getpass
from time import sleep
import json


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

print ("Conectado! \n")

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
            mensagem = {
                    'acao': 'login', 
                    'login': str(login), 
                    'senha': str(senha)
                }
            #Msg no formato JSON
            jmensagem = json.dumps(mensagem)
            # Envia e recebe mensagem para o servidor
            sock.send(jmensagem + '\n')
            recebido = False
            resposta = ''
            while not recebido:
                try:
                    recebidas, _, _ = select.select(entrada_saida, [], [],10)
                    if recebidas:
                        resposta = sock.recv(1024)
                        jresposta = json.loads(resposta, 'utf-8')
                        if len(jresposta) != 0 and jresposta != '\n':
                                recebido = True
                                print('\n'+ jresposta['resposta'] +'\n')
                                
                except:
                    print("Nenhuma resposta recebida!!")

            if jresposta['resposta'] == "Login Realizado!":
                logado = True
            else:
                print(resposta)

        elif opt == "2":
            print("\nCadastro")
            nome = raw_input("Nome do Cliente: ")
            login = raw_input("Login: ")
            senha = getpass.getpass("Senha: ")
            cpf = raw_input("Cpf: ")
            endereco = raw_input("Endereço: ")
            mensagem = {
                    'acao':'cadastro', 
                    'nome': str(nome), 
                    'login': str(login), 
                    'senha': str(senha),
                    'cpf': str(cpf),
                    'endereco': str(endereco)
                }
            # Envia para o servidor a requisição de cadastro e a pessoa
            jmensagem = json.dumps(mensagem)
            sock.send(jmensagem + '\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [],10)
            if recebidas:
                resposta = sock.recv(1024)
                jresposta = json.loads(resposta, 'utf-8') 
                if len(jresposta) != 0:
                    print('\n'+ jresposta['resposta'] +'\n')
            
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
            mensagem = {
                'acao': 'emprestarLivro', 
                'titulo': str(titulo_livro),
                'isbn': str(isbn),
                'nome': str(nome)
            }
            jmensagem = json.dumps(mensagem)
            sock.send(jmensagem+'\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [], 10)
            if recebidas:
                resposta = sock.recv(1024)
                jresposta = json.loads(resposta, 'utf-8')
                if len(jresposta) != 0 and jresposta != '\n':
                    print('\n' + jresposta['resposta'] + '\n')
        elif opt == "2":
            mensagem = {
                'acao': 'listarLivros'
                }
            jmensagem = json.dumps(mensagem)
            sock.send(jmensagem+'\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [],10)
            if recebidas:
                resposta = sock.recv(1024)
                jresposta = json.loads(resposta, 'utf-8')
                if len(jresposta) != 0 and jresposta != '\n':
                    print(jresposta['resposta'].replace(']','\n'))
        elif opt == "3":
            print("\nDados do Livro!")
            isbn = raw_input("Código do Livro(ISBN): ", )
            titulo_livro = raw_input("Titulo do Livro: ", )
            mensagem = {
                'acao': 'reservarLivro', 
                'titulo': str(titulo_livro),
                'isbn': str(isbn),
                'nome': str(nome)
            }
            jmensagem = json.dumps(mensagem)
            sock.send(jmensagem+'\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [], 10)
            if recebidas:
                resposta = sock.recv(1024)
                jresposta = json.loads(resposta, 'utf-8')
                if len(jresposta) != 0 and jresposta != '\n':
                    print('\n' + jresposta['resposta'] + '\n')
        elif opt == "4":
            print("\nDados do Livro!")
            isbn = raw_input("Código do Livro(ISBN): ", )
            titulo_livro = raw_input("Titulo do Livro: ", )
            mensagem = {
                'acao': 'devolverLivro', 
                'titulo': str(titulo_livro),
                'isbn': str(isbn),
                'nome': str(nome)
            }
            jmensagem = json.dumps(mensagem)
            sock.send(jmensagem+'\n')
            resposta = ''
            recebidas, _, _ = select.select(entrada_saida, [], [], 10)
            if recebidas:
                resposta = sock.recv(1024)
                jresposta = json.loads(resposta, 'utf-8')
                if len(jresposta) != 0 and jresposta != '\n':
                    print('\n' + jresposta['resposta'] + '\n')
        elif opt == "5":
            print("\nDados do Livro!")
            isbn = raw_input("Código do Livro (ISBN): ", )
            titulo_livro = raw_input("Título do Livro: ", )
            mensagem = {
                'acao': 'buscarLivro',
                'titulo': str(titulo_livro),
                'isbn': str(isbn)
            }
            jmensagem = json.dumps(mensagem)
            sock.send(jmensagem+'\n')
            resposta = ''
            recebidas,_,_ = select.select(entrada_saida, [], [], 10)
            if recebidas:
                resposta = sock.recv(1024)
                jresposta = json.loads(resposta, 'utf-8')
                if len(jresposta) != 0 and jresposta != '\n':
                    print('\n' + jresposta['resposta'] + '\n')
        elif opt == "6":
            logado = False
        else:
            print("OPÇÃO NÃO SUPORTADA!!")
 
sock.close()
