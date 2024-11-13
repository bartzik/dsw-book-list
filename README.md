<h1>BookList</h1>

<h2>Back-end do projeto de Desenvolvimento WEB e Projeto Integrador II</h2>

<p>O <strong>BookList</strong> é uma aplicação web para gerenciamento de livros e interações de usuários leitores e administradores. A plataforma permite que leitores avaliem livros e façam comentários, enquanto administradores podem gerenciar a base de dados de livros, seus autores e editoras.</p>

<h2>Funcionalidades</h2>

<ul>
  <li><strong>Leitores</strong>:
    <ul>
      <li>Visualização de todos os livros disponíveis.</li>
      <li>Visualização de detalhes de cada livro, incluindo os comentários de outros leitores.</li>
      <li>Adição de comentários em livros.</li>
    </ul>
  </li>
  <li><strong>Administradores</strong>:
    <ul>
      <li>Cadastro de novos livros, autores e editoras.</li>
      <li>Gerenciamento de informações.</li>
      <li>Acesso exclusivo a uma página de gerenciamento após login, com opções para cadastrar e gerenciar livros, autores e editoras.</li>
    </ul>
  </li>
</ul>

<h2>Tecnologias Utilizadas</h2>

<ul>
  <li><strong>Back-end</strong>: Desenvolvido com Java e Spring Boot, utilizando uma API REST para comunicação com o front-end.</li>
  <li><strong>Front-end</strong>: Construído com React, para uma experiência de usuário interativa e responsiva.</li>
  <li><strong>Banco de Dados</strong>: Conexão com PostgreSQL.</li>
</ul>

<h2>Endpoints Principais</h2>

<ul>
  <li><code>GET /books</code>: Retorna todos os livros cadastrados.</li>
  <li><code>GET /books/{id}</code>: Retorna os detalhes de um livro específico.</li>
  <li><code>POST /books</code>: Cadastra um novo livro (acessível apenas para administradores).</li>
  <li><code>POST /comments</code>: Permite adicionar um comentário a um livro (acessível para leitores).</li>
</ul>


