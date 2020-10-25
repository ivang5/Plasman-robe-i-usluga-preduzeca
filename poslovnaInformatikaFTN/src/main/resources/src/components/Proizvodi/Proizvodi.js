import React, { Component } from "react";
import styled from "styled-components";
import Proizvod from "./Proizvod";
import ProizvodModal from "./ProizvodModal";
import CreateProizvodModal from "./CreateProizvodModal";
import "../datatables.css";
import { Redirect } from "react-router-dom";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class Proizvodi extends Component {
  state = {
    proizvodi: [],
    grupeProizvoda: [],
    urlProizvodi: "http://localhost:8080/api/proizvodi/",
    urlGrupeProizvoda: "http://localhost:8080/api/grupeProizvoda/",
    modalOpen: false,
    modalCreate: false,
    modalProizvod: {},
    modalGrupaProizvoda: "",
    error: "",
    preuzetiProizvodi: false,
    preuzeteGrupe: false,
    login: null,
  };

  // Preuzimanje svih proizvoda sa servera
  async getProizvodi() {
    try {
      const data = await fetch(this.state.urlProizvodi, {
        headers: {
          "Content-Type": "application/json",
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
          return { proizvodi: jsonData, preuzetiProizvodi: true };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje svih grupa proizvoda sa servera
  async getGrupeProizvoda() {
    try {
      const data = await fetch(this.state.urlGrupeProizvoda, {
        headers: {
          "Content-Type": "application/json",
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
          return { grupeProizvoda: jsonData, preuzeteGrupe: true };
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
    this.getProizvodi().then(() => {
      if (this.state.preuzetiProizvodi && this.state.preuzeteGrupe) {
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
      }
    });
  }

  // Brisanje proizvoda iz tabele
  deleteProizvod = (id) => {
    fetch(this.state.urlProizvodi + id, {
      method: "DELETE",
    })
      .then((res) => {
        res.text();
        console.log(res);
        this.getProizvodi();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  getProizvod = (id) => {
    const proizvod = this.state.proizvodi.find((item) => item.id === id);
    return proizvod;
  };

  getGrupaProizvoda = (id) => {
    const grupaProizvoda = this.state.grupeProizvoda.find(
      (item) => item.id === id
    );
    const nazivGrupe = grupaProizvoda.naziv;
    return nazivGrupe;
  };

  openModal = (id) => {
    const proizvod = this.getProizvod(id);
    const grupaProizvoda = this.getGrupaProizvoda(proizvod.idGrupe);
    this.setState({
      modalOpen: true,
      modalGrupaProizvoda: grupaProizvoda,
      modalProizvod: proizvod,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
      modalCreate: false,
    });
  };

  // Cuvanje izmena proizvoda
  saveModal = (id, naziv, jedinicaMere, opis, tipProizvoda, grupaProizvoda) => {
    const proizvod = this.getProizvod(id);

    const nazivConst = naziv === "" ? proizvod.naziv : naziv;
    const jedinicaMereConst =
      jedinicaMere === "" ? proizvod.jedinicaMere : jedinicaMere;
    const opisConst = opis === "" ? proizvod.opis : opis;
    const tipProizvodaConst =
      tipProizvoda === "" ? proizvod.tipProizvoda : tipProizvoda;
    const grupaProizvodaConst =
      grupaProizvoda === "" ? proizvod.idGrupe : grupaProizvoda;

    const data = {
      id: id,
      naziv: nazivConst,
      jedinicaMere: jedinicaMereConst,
      opis: opisConst,
      tipProizvoda: tipProizvodaConst,
      idGrupe: grupaProizvodaConst,
    };
    fetch(this.state.urlProizvodi, {
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
        this.getProizvodi();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  createModal = () => {
    this.setState({
      modalCreate: true,
    });
  };

  // Cuvanje novog proizvoda
  createProizvod = (
    naziv,
    jedinicaMere,
    opis,
    tipProizvoda,
    grupaProizvoda
  ) => {
    const tipProizvodaConst = tipProizvoda === "" ? 0 : tipProizvoda;
    const grupaProizvodaConst = grupaProizvoda === "" ? 1 : grupaProizvoda;
    const data = {
      naziv: naziv,
      jedinicaMere: jedinicaMere,
      opis: opis,
      tipProizvoda: tipProizvodaConst,
      idGrupe: grupaProizvodaConst,
    };
    fetch(this.state.urlProizvodi, {
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
        this.getProizvodi();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    if (this.state.preuzetiProizvodi && this.state.preuzeteGrupe) {
      return (
        <div>
          <TableWrapper
            id="tabelaProizvoda"
            className="col-11 mx-auto mt-5 table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "20%" }}>Naziv</th>
                <th style={{ width: "10%" }}>Jedinica mere</th>
                <th style={{ width: "25%" }}>Opis</th>
                <th style={{ width: "15%" }}>Tip</th>
                <th style={{ width: "30%" }}>Grupa proizvoda</th>
                <th>Izmena</th>
                <th>Brisanje</th>
              </tr>
            </thead>
            <tbody>
              {this.state.proizvodi.map((proizvod) => {
                const grupa = this.getGrupaProizvoda(proizvod.idGrupe);
                return (
                  <Proizvod
                    key={proizvod.id}
                    proizvod={proizvod}
                    grupa={grupa}
                    deleteProizvod={() => this.deleteProizvod(proizvod.id)}
                    openModal={() => this.openModal(proizvod.id)}
                  />
                );
              })}
            </tbody>
          </TableWrapper>
          <ButtonWrapper
            className="btn btn-success float-right mt-4 px-4"
            onClick={this.createModal}
          >
            Dodaj proizvod
          </ButtonWrapper>
          <ProizvodModal
            modalOpen={this.state.modalOpen}
            modalProizvod={this.state.modalProizvod}
            grupeProizvoda={this.state.grupeProizvoda}
            closeModal={this.closeModal}
            saveModal={this.saveModal}
          />
          <CreateProizvodModal
            modalCreate={this.state.modalCreate}
            grupeProizvoda={this.state.grupeProizvoda}
            closeModal={this.closeModal}
            createProizvod={this.createProizvod}
          />
        </div>
      );
    } else {
      return <React.Fragment />;
    }
  }
}

const TableWrapper = styled.table`
  th {
    border: 1px solid black;
    text-align: center;
    vertical-align: middle !important;
    padding: 1px !important;
    height: 2rem;
  }
`;

const ButtonWrapper = styled.button`
  margin-right: 4.16%;
`;
