import React, { Component } from "react";
import GrupaProizvoda from "./GrupaProizvoda";
import { Redirect } from "react-router-dom";
import styled from "styled-components";
import CreateGrupaProizvodaModal from "./CreateGrupaProizvodaModal";

export default class GrupeProizvoda extends Component {
  state = {
    grupeProizvoda: [],
    proizvodi: [],
    pdvKategorije: [],
    urlGrupeProizvoda: "http://localhost:8080/api/grupeProizvoda/",
    urlProizvodi: "http://localhost:8080/api/proizvodi/",
    urlPdvKategorije: "http://localhost:8080/api/pdvKategorije/",
    modalCreateOpen: false,
    error: "",
    login: null,
  };

  // Preuzimanje svih grupa proizvoda sa servera
  async getGrupeProizvoda() {
    try {
      const data = await fetch(this.state.urlGrupeProizvoda, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjena nijedna grupa proizvoda" };
        });
      } else {
        this.setState(() => {
          return { grupeProizvoda: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje svih proizvoda sa servera
  async getProizvodi() {
    try {
      const data = await fetch(this.state.urlProizvodi, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan proizvod" };
        });
      } else {
        this.setState(() => {
          return { proizvodi: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje PDV kategorija sa servera
  async getPdvKategorije() {
    try {
      const data = await fetch(this.state.urlPdvKategorije, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjena nijedna PDV kategorija" };
        });
      } else {
        this.setState(() => {
          return { pdvKategorije: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  componentDidMount() {
    if (
      sessionStorage.getItem("token") === null ||
      sessionStorage.getItem("token") === undefined
    ) {
      this.setState({
        login: "/",
      });
    }
    this.getGrupeProizvoda();
    this.getProizvodi();
    this.getPdvKategorije();
  }

  getPdvKategorija = (id) => {
    if (this.state.pdvKategorije.length < 1) {
      return "";
    } else {
      const pdvKategorija = this.state.pdvKategorije.find(
        (item) => item.id === id
      );
      const nazivPdvKategorije = pdvKategorija.naziv;
      return nazivPdvKategorije;
    }
  };

  openCreateModal = () => {
    this.setState({
      modalCreateOpen: true,
    });
  };

  closeModal = () => {
    this.setState({
      modalCreateOpen: false,
    });
  };

  // Dodavanje nove grupe proizvoda u tabelu
  createGrupaProizvoda = (naziv, idPdvKategorije, proizvodi) => {
    let proizvodiObjects = [];
    proizvodi.forEach((proizvod) => {
      var found = this.state.proizvodi.find(
        // eslint-disable-next-line
        (element) => element.id == proizvod
      );
      proizvodiObjects.push(found);
    });
    const data = {
      naziv: naziv,
      proizvodi: proizvodiObjects,
      idPdvKategorije: idPdvKategorije,
    };
    fetch(this.state.urlGrupeProizvoda, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token"),
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        res.text();
        console.log(res);
        this.closeModal();
        this.getGrupeProizvoda();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  // Brisanje grupe proizvoda iz tabele
  deleteGrupa = (id) => {
    fetch(this.state.urlGrupeProizvoda + id, {
      method: "DELETE",
    })
      .then((res) => {
        res.text();
        console.log(res);
        this.getGrupeProizvoda();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    return (
      <React.Fragment>
        {this.state.grupeProizvoda.map((grupaProizvoda) => {
          const pdvKategorija = this.getPdvKategorija(
            grupaProizvoda.idPdvKategorije
          );
          return (
            <GrupaProizvoda
              key={grupaProizvoda.id}
              grupaProizvoda={grupaProizvoda}
              sviProizvodi={this.state.proizvodi}
              pdvKategorije={this.state.pdvKategorije}
              pdvKategorija={pdvKategorija}
              deleteGrupa={() => this.deleteGrupa(grupaProizvoda.id)}
            />
          );
        })}
        <ButtonWrapper
          className="btn btn-success float-right mt-5 mb-5 px-4"
          onClick={this.openCreateModal}
        >
          Dodaj novu grupu
        </ButtonWrapper>
        <CreateGrupaProizvodaModal
          modalCreateOpen={this.state.modalCreateOpen}
          closeModal={this.closeModal}
          createGrupaProizvoda={this.createGrupaProizvoda}
          proizvodi={this.state.proizvodi}
          pdvKategorije={this.state.pdvKategorije}
        />
      </React.Fragment>
    );
  }
}

const ButtonWrapper = styled.button`
  margin-right: 4.16%;
`;
