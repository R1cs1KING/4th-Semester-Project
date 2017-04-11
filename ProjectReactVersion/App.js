import React from 'react';
import { StyleSheet, Text, View, Button, TextInput } from 'react-native';
import { Constants } from 'expo';

export default class App extends React.Component {
  state = {text : 'def', text1 : 'e-mail', text2 : 'password', text3 : 'type e-mail', text4 : 'type password', btn1 : 'Log in'}

    render() {
    return (
      <View style={styles.container}>
        <Text style={styles.paragraph}>
         {this.state.text1}
        </Text>
        <TextInput
        style={styles.editable}
        onChangeText={(text) => this.setState({text})}
        value={this.state.text3}
      />
        <Text style={styles.paragraph}>
         {this.state.text2}
        </Text>
        <TextInput
        style={styles.editable}
        onChangeText={(text) => this.setState({text})}
        value={this.state.text4}
      />
      <View style={{flex: 0.3, margin: 12, flexDirection:'column', justifyContent: 'space-around'}}>
        <Button 
        color='gray'
         title='Log in' />
         </View>
         <View style={{flex: 0.3, margin: 12, flexDirection:'column', justifyContent: 'space-around'}}>
         <Button
         color='gray'
         title='Register' />
          </View>
          <View style ={{flex: 0.5}}>
            </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    paddingTop: Constants.statusBarHeight,
    backgroundColor: '#64B046',
  },
  paragraph: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
    color: '#34495e',
  },
  editable: {
    height: 40,
    width: 180,
    borderColor: 'gray',
    borderWidth: 1,
    margin: 24,
    backgroundColor: '#D3D3D3',
    textAlign: 'center',
  },
});
