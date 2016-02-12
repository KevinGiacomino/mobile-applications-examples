Rails.application.routes.draw do

  get 'callback', to: 'callback#index'
  get 'userinfo', to: 'userinfo#index'

end
