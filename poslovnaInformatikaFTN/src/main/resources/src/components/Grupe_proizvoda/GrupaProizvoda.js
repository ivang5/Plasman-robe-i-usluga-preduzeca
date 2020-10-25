import React, { Component } from "react";
import styled from "styled-components";
import "../datatables.css";
import GrupaProizvodaModal from "./GrupaProizvodaModal";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class GrupaProizvoda extends Component {
  state = {
    modalOpen: false,
    grupeProizvoda: [],
    proizvodi: [],
    urlGrupeProizvoda: "http://localhost:8080/api/grupeProizvoda/",
    urlProizvodi: "http://localhost:8080/api/proizvodi/",
  };

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

  componentDidMount() {
    this.getProizvodi().then(() => {
      this.$tableRef = $(this.tableRef);
      this.$tableRef.DataTable({
        bInfo: false,
        oLanguage: {
          sEmptyTable: "Nema dostupnih podataka u tabeli",
          sZeroRecords: "Nema rezultata pretrage",
          sSearch: "Pretraga:",
          oPaginate: {
            sNext: "Sledeća",
            sPrevious: "Prethodna",
          },
          sLengthMenu: "Prikaži _MENU_ redova",
        },
      });
    });
  }

  openModal = () => {
    this.setState({
      modalOpen: true,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
    });
  };

  saveModal = (id, naziv, idPdvKategorije, proizvodi) => {
    let proizvodiObjects = [];
    proizvodi.forEach((proizvod) => {
      var found = this.state.proizvodi.find(
        // eslint-disable-next-line
        (element) => element.id == proizvod
      );
      proizvodiObjects.push(found);
    });
    const data = {
      id: id,
      naziv: naziv,
      idPdvKategorije: idPdvKategorije,
      proizvodi: proizvodiObjects,
    };
    console.log("id: " + data.id);
    console.log("naziv: " + data.naziv);
    console.log("idPdvKategorije: " + data.idPdvKategorije);
    console.log("proizvod: " + data.proizvodi[0].naziv);

    fetch(this.state.urlGrupeProizvoda, {
      method: "PUT",
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
        window.location.reload(false);
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    const {
      pdvKategorija,
      grupaProizvoda,
      deleteGrupa,
      sviProizvodi,
      pdvKategorije,
    } = this.props;
    const { naziv, proizvodi } = this.props.grupaProizvoda;

    if (proizvodi.length < 1) {
      return (
        <React.Fragment>
          <GrupaWrapper>
            <h2 className="title-center">Naziv grupe: {naziv}</h2>
            <h4 className="title-center">PDV kategorija: {pdvKategorija}</h4>
            <h6 className="title-center">
              Ova kategorija trenutno nema nijedan proizvod/uslugu. Ukoliko
              želite da ih dodate, to možete učiniti na stranici sa proizvodima.
            </h6>
            <ButtonWrapper
              className="btn btn-danger float-right mt-1 px-4"
              onClick={deleteGrupa}
            >
              Izbriši grupu
            </ButtonWrapper>
            <ButtonWrapper
              className="btn btn-primary float-right mt-1 px-4"
              onClick={this.openModal}
            >
              Promeni grupu
            </ButtonWrapper>
            <hr />
          </GrupaWrapper>
          <GrupaProizvodaModal
            grupaProizvoda={grupaProizvoda}
            sviProizvodi={sviProizvodi}
            pdvKategorije={pdvKategorije}
            modalOpen={this.state.modalOpen}
            closeModal={this.closeModal}
            saveModal={this.saveModal}
          />
        </React.Fragment>
      );
    }
    return (
      <React.Fragment>
        <GrupaWrapper>
          <h2 id="naziv-gupe" className="title-center">
            Naziv grupe: {naziv}
          </h2>
          <h4 className="title-center">PDV kategorija: {pdvKategorija}</h4>
          <table
            id="tabelaProizvoda"
            className="col-11 mx-auto mt-4 table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "30%" }}>Naziv</th>
                <th style={{ width: "10%" }}>Jedinica mere</th>
                <th style={{ width: "45%" }}>Opis</th>
                <th style={{ width: "15%" }}>Tip</th>
              </tr>
            </thead>
            <tbody>
              {proizvodi.map((proizvod) => {
                return (
                  <tr key={proizvod.id}>
                    <td>{proizvod.naziv}</td>
                    <td>{proizvod.jedinicaMere}</td>
                    <td>{proizvod.opis}</td>
                    <td>{proizvod.tipProizvoda}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
          <ButtonWrapper
            className="btn btn-danger float-right mt-3 px-4"
            onClick={deleteGrupa}
          >
            Izbriši grupu
          </ButtonWrapper>
          <ButtonWrapper
            className="btn btn-primary float-right mt-3 px-4"
            onClick={this.openModal}
          >
            Promeni grupu
          </ButtonWrapper>
          <hr />
        </GrupaWrapper>
        <GrupaProizvodaModal
          grupaProizvoda={grupaProizvoda}
          sviProizvodi={sviProizvodi}
          pdvKategorije={pdvKategorije}
          modalOpen={this.state.modalOpen}
          closeModal={this.closeModal}
          saveModal={this.saveModal}
        />
      </React.Fragment>
    );
  }
}

const GrupaWrapper = styled.div`
  .title-center {
    text-align: center;
    margin-bottom: 1rem;
  }
  hr {
    margin-top: 4rem;
  }
  th {
    border: 1px solid black;
    text-align: center;
    vertical-align: middle !important;
    padding: 1px !important;
    height: 2rem;
  }
`;

const ButtonWrapper = styled.button`
  width: 13rem;
  margin-right: 4.16%;
`;
