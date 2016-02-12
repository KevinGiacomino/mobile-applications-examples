class UserinfoController < ApplicationController

  FC_BASE_URL = "https://fcp.integ01.dev-franceconnect.fr"
  FC_TOKEN_ENDPOINT = "/api/v1/token"
  FC_USERINFO_ENDPOINT = "/api/v1/userinfo?schema=openid"

  FC_CLIENT_ID = "YOUR CLIENT ID"
  FC_CLIENT_SECRET = "YOUR SECRET KEY"
  FC_CALLBACK_URL = "YOUR CALLBACK URL"

  def index
    # Fetch FC's token
    code = params[:code]
    nonce = params[:nonce]

    if code.blank? || nonce.blank?
      error = { code: 401, message: "Unauthorized: You must provide 'code' and 'nonce' GET parameters" }
      render json: error, status: 401
      return
    end

    uri = URI(FC_BASE_URL + FC_TOKEN_ENDPOINT)
    @https = Net::HTTP.new(uri.host, uri.port)
    @https.use_ssl = true

    params = { 
      grant_type: 'authorization_code', 
      redirect_uri: FC_CALLBACK_URL,
      client_id: FC_CLIENT_ID,
      client_secret: FC_CLIENT_SECRET,
      code: code
    }

    token_request = Net::HTTP::Post.new uri
    token_request.content_type = 'application/json'
    token_request.body = params.to_json

    token_response = @https.request token_request

    if token_response.code != '200'
      error = { code: 500, fc_code: token_response.code.to_i, fc_message: token_response.message }
      render json: error, status: 500
      return
    end

    token_response_json = JSON.parse(token_response.body)
    token = token_response_json['access_token']

    uri = URI(FC_BASE_URL + FC_USERINFO_ENDPOINT)
    info_request = Net::HTTP::Get.new(uri)
    info_request['Authorization'] = 'Bearer ' + token

    info_response = @https.request info_request

    if info_response.code != '200'
      error = { code: 500, fc_code: info_response.code.to_i, fc_message: info_response.message }
      render json: error, status: 500
      return
    end

    info_response_json = JSON.parse(info_response.body)
    render json: info_response_json
  end

end
