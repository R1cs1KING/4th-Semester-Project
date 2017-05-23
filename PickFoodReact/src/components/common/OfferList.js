import React, { Component } from 'react';
import { ScrollView } from 'react-native';
import axios from 'axios';
import OfferDetail from './OfferDetail';

class OfferList extends Component {
  state = { offers: [] };

  componentWillMount() {
    axios.get('https://console.firebase.google.com/project/pickfood-5c351/database/data/Food')
    .then(response => this.setState({ offers: response.data }));
  }

  renderOffers() {
    return this.state.offers.map(offer =>
    <OfferDetail key={offer.title} offer={offer} />
  );
  }

  render() {
    console.log(this.state);

  return (
    <ScrollView>
      {this.renderOffers()}
    </ScrollView>
  );
}
}

export { OfferList };
