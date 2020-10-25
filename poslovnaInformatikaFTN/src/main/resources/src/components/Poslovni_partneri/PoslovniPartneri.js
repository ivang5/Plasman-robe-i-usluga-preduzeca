import React, { Component } from "react";
import styled from "styled-components";
import PoslovniPartner from "./PoslovniPartner";
import PoslovniPartnerModal from "./PoslovniPartnerModal";
import CreatePoslovniPartnerModal from "./CreatePoslovniPartnerModal";
import "../datatables.css";
import { Redirect } from "react-router-dom";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class PoslovniPartneri extends Component {
  state = {
    poslovniPartneri: [],
    urlPoslovniPartneri: "http://localhost:8080/api/poslovniPartneri/",
    modalPoslovniPartner: {},
    modalOpen: false,
    modalCreate: false,
    error: "",
    preuzetiPoslovniPartneri: false,
    login: null,
  };

  // Preuzimanje svih poslovnih partnera sa servera
  async getPoslovniPartneri() {
    try {
      const data = await fetch(this.state.urlPoslovniPartneri, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan poslovni partner" };
        });
      } else {
        this.setState(() => {
          return { poslovniPartneri: jsonData, preuzetiPoslovniPartneri: true };
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
    this.getPoslovniPartneri().then(() => {
      if (this.state.preuzetiPoslovniPartneri) {
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

  // Brisanje poslovnog partnera iz tabele
  deletePoslovniPartner = (id) => {
    fetch(this.state.urlPoslovniPartneri + id, {
      method: "DELETE",
    })
      .then((res) => {
        res.text();
        console.log(res);
        this.getPoslovniPartneri();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  getPoslovniPartner = (id) => {
    const poslovniPartner = this.state.poslovniPartneri.find(
      (item) => item.id === id
    );
    return poslovniPartner;
  };

  openModal = (id) => {
    const poslovniPartner = this.getPoslovniPartner(id);
    this.setState({
      modalOpen: true,
      modalPoslovniPartner: poslovniPartner,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
      modalCreate: false,
    });
  };

  createModal = () => {
    this.setState({
      modalCreate: true,
    });
  };

  // Cuvanje izmena poslovnog partnera
  saveModal = (
    id,
    pib,
    naziv,
    vrstaPartnera,
    racuniUBanci,
    adresa,
    telefon,
    email
  ) => {
    const poslovniPartner = this.getPoslovniPartner(id);

    const pibConst = pib === "" ? poslovniPartner.pib : pib;
    const nazivConst = naziv === "" ? poslovniPartner.naziv : naziv;
    const vrstaPartneraConst =
      vrstaPartnera === "" ? poslovniPartner.vrstaPartnera : vrstaPartnera;
    const racuniUBanciConst =
      racuniUBanci === "" ? poslovniPartner.racuniUBanci : racuniUBanci;
    const adresaConst = adresa === "" ? poslovniPartner.adresa : adresa;
    const telefonConst = telefon === "" ? poslovniPartner.telefon : telefon;
    const emailConst = email === "" ? poslovniPartner.email : email;

    const data = {
      id: id,
      pib: pibConst,
      naziv: nazivConst,
      vrstaPartnera: vrstaPartneraConst,
      racuniUBanci: racuniUBanciConst,
      adresa: adresaConst,
      telefon: telefonConst,
      email: emailConst,
    };
    fetch(this.state.urlPoslovniPartneri, {
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
        this.getPoslovniPartneri();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  // Cuvanje novog poslovnog partnera
  createPoslovniPartner = (
    pib,
    naziv,
    vrstaPartnera,
    racuniUBanci,
    adresa,
    telefon,
    email
  ) => {
    const vrstaPartneraConst = vrstaPartnera === "" ? "KUPAC" : vrstaPartnera;

    console.log("pib: " + pib);
    console.log("naziv: " + naziv);
    console.log("vrstaPartnera: " + vrstaPartneraConst);
    console.log("racuniUBanci: " + racuniUBanci);
    console.log("adresa: " + adresa);
    console.log("telefon: " + telefon);
    console.log("email: " + email);

    const data = {
      pib: pib,
      naziv: naziv,
      vrstaPartnera: vrstaPartneraConst,
      racuniUBanci: racuniUBanci,
      adresa: adresa,
      telefon: telefon,
      email: email,
    };
    fetch(this.state.urlPoslovniPartneri, {
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
        this.getPoslovniPartneri();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    if (this.state.preuzetiPoslovniPartneri) {
      return (
        <div>
          <TableWrapper
            id="tabelaPoslovnihParnera"
            className="col-11 mx-auto mt-5 table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "10%" }}>PIB</th>
                <th style={{ width: "15%" }}>Naziv</th>
                <th style={{ width: "10%" }}>Vrsta partnera</th>
                <th style={{ width: "30%" }}>Racuni u banci</th>
                <th style={{ width: "15%" }}>Adresa</th>
                <th style={{ width: "10%" }}>Broj telefona</th>
                <th style={{ width: "10%" }}>Email</th>
                <th>Izmena</th>
                <th>Brisanje</th>
              </tr>
            </thead>
            <tbody>
              {this.state.poslovniPartneri.map((poslovniPartner) => {
                return (
                  <PoslovniPartner
                    key={poslovniPartner.id}
                    poslovniPartner={poslovniPartner}
                    deletePoslovniPartner={() =>
                      this.deletePoslovniPartner(poslovniPartner.id)
                    }
                    openModal={() => this.openModal(poslovniPartner.id)}
                  />
                );
              })}
            </tbody>
          </TableWrapper>
          <ButtonWrapper
            className="btn btn-success float-right mt-4 px-4"
            onClick={this.createModal}
          >
            Dodaj partnera
          </ButtonWrapper>
          <PoslovniPartnerModal
            modalOpen={this.state.modalOpen}
            modalPoslovniPartner={this.state.modalPoslovniPartner}
            closeModal={this.closeModal}
            saveModal={this.saveModal}
          />
          <CreatePoslovniPartnerModal
            modalCreate={this.state.modalCreate}
            closeModal={this.closeModal}
            createPoslovniPartner={this.createPoslovniPartner}
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
