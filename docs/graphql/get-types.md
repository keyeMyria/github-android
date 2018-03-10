```graphql
query {
  __schema {
    types {
      name
      kind
      description
      fields {
        name
        description
        type{
          name
        }
      }
    	interfaces{
        name
        description
        kind
        inputFields{
          name
        }
      }
    }
  }
}
```